package io.socialtech.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static io.socialtech.Conf.WEB_URL;

public class DashboardPage extends AbstractPage {

    public DashboardPage() {
        page(this);
    }

    public void  openPage() {
        open(WEB_URL);
    }

    @FindBy(name="search")
    private SelenideElement searchInput;

    @FindBy(xpath="//*[contains(@class,'search-form__submit')]")
    private SelenideElement submit;

    public void typeToSearchField(String criteria) {
        searchInput.val(criteria);
    }

    public void clickSubmitButton(){
        submit.click();
    }
}
