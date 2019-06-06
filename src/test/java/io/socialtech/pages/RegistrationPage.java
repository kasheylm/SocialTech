package io.socialtech.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static io.socialtech.Conf.WEB_URL;

public class RegistrationPage extends AbstractPage{

    public RegistrationPage() {
        page(this);
    }

    @FindBy(name="title")
    private SelenideElement name;

    @FindBy(name="login")
    private SelenideElement login;

    @FindBy(name="password")
    private SelenideElement password;

    @FindBy(xpath="//*[@class='signup-submit']//button")
    private SelenideElement submit;

    @FindBy(xpath="//*[@name='profile-content']//*[@name='app-message']")
    private SelenideElement message;

    @FindBy(xpath="(//*[contains(@class,'profile-info')])[2]")
    private SelenideElement email;

    public void openPage() {
        open(WEB_URL.replace("rozetka", "my.rozetka") + "/signup");
    }

    public void setName(String userName) {
        name.val(userName);
    }

    public void setLogin(String userLogin) {
        login.val(userLogin);
    }

    public void setPassword(String userPass) {
        password.val(userPass);
    }

    public void clickSubmit(){
        submit.click();
    }

    public void messageEmailApprovedShouldBeVisible(){
        message.shouldBe(visible);
    }

    public String getEmailFromProfile(){
        return email.text();
    }
}
