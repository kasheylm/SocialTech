package io.socialtech;

import io.socialtech.api.TempMailService;
import io.socialtech.pages.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AcceptanceTests extends BaseTest{

    public static final String USER_NAME = "Чупакабра";
    public static final String USER_PASS = "Z11111111";

    @Test
    public void userRegistrationTest() {
        Map<String, String> tempEmail = TempMailService.getTempEmail();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.openPage();
        registrationPage.setName(USER_NAME);
        registrationPage.setLogin(tempEmail.get("mail"));
        registrationPage.setPassword(USER_PASS);
        registrationPage.clickSubmit();

        open(TempMailService.getConfirmationLinkFromEmail(tempEmail.get("token")));

        registrationPage.messageEmailApprovedShouldBeVisible();
        assertThat(registrationPage.getEmailFromProfile(), is (tempEmail.get("mail")));
    }

    @Test(dataProvider="getPhones")
    public void validationPhoneCharacteristicsTest(String model, String weight, String display, String memory) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.openPage();
        dashboardPage.typeToSearchField(model);
        dashboardPage.clickSubmitButton();

        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.selectCategory("Мобильные телефоны");
        searchResultPage.openFirstProduct();

        ProductPage productPage = new ProductPage();
        productPage.openProductDetails();

        ProductDetailsPage detailsPage = new ProductDetailsPage();
        detailsPage.readAllCharacteristics();
        assertThat("Вес телефона отличается либо отсутствует", detailsPage.getValueByProperty("Вес, г"), equalTo(weight));
        assertThat("Диагональ телефона отличается либо отсутствует", detailsPage.getValueByProperty("Диагональ экрана"), equalTo(display));
        assertThat("Память телефона отличается либо отсутствует", detailsPage.getValueByProperty("Оперативная память"), equalTo(memory));
    }

    @DataProvider
    public Object[][] getPhones()
    {
        return new Object[][]
            {
                {"Apple IPhone 7",      "138", "4.7", "2 ГБ"},
                {"Apple IPhone 7 Plus", "188", "5.5", "3 ГБ"}
            };
    }
}
