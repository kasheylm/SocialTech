package io.socialtech.api;

import com.googlecode.junittoolbox.PollingWait;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.socialtech.Conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class TempMailService {

    public static final String PREFIX = "https://my.rozetka.com.ua/authorize";
    private static final PollingWait wait = new PollingWait().pollEvery(5, TimeUnit.SECONDS).timeoutAfter(2, TimeUnit.MINUTES);

    static {
        RestAssured.filters( new ResponseLoggingFilter(LogDetail.ALL),
                             new RequestLoggingFilter(LogDetail.ALL) );
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = Conf.API_URL;
    }

    public static Map<String, String> getTempEmail() {
        Map<String, String> mailData = new HashMap<>();
        Response response = given().
                queryParam("f", "get_email_address").
                queryParam("lang", "en").
                get();
        mailData.put("mail", response.jsonPath().get("email_addr"));
        mailData.put("token", response.jsonPath().getString("sid_token"));
        return mailData;
    }

    private static int waitForMailByText(String token, String stringToWait) {
        final int[] ids = {0};
        wait.until(() -> {
            Response response = given().
                    queryParam("f", "check_email").
                    queryParam("lang", "en").
                    queryParam("seq", "0").
                    queryParam("sid_token", token).
                    get();
            try {
                List<Map<String, String>> letters = response.jsonPath().getList("list");
                int id = isInboxContainsNeededMail(letters, stringToWait);
                if (id > 0) {
                    ids[0] = id;
                    return true;
                }
            } catch (Exception e) {
            }
            return false;
        });
        return ids[0];
    }

    private static String getMailBody(String token, int msgId) {
        Response response = given().
                queryParam("f", "fetch_email").
                queryParam("lang", "en").
                queryParam("sid_token", token).
                queryParam("email_id", msgId).
                get();
        response.prettyPrint();
        return response.jsonPath().getString("mail_body");
    }

    private static String extractLinkFromEmail(String mailBody) {
        int start = mailBody.indexOf(PREFIX);
        int end = mailBody.indexOf("\"", start);
        return mailBody.substring(start, end);
    }

    private static int isInboxContainsNeededMail(List<Map<String, String>> letters, String textToLookUp) {
        for (Map<String, String> letter : letters) {
            if (letter.get("mail_from").contains(textToLookUp) )
                return Integer.parseInt(letter.get("mail_id"));
        }
        return 0;
    }

    public static String getConfirmationLinkFromEmail(String token) {
        int mailId = TempMailService.waitForMailByText(token, "rozetka");
        String mailBody = TempMailService.getMailBody(token, mailId);
        return TempMailService.extractLinkFromEmail(mailBody);
    }
}
