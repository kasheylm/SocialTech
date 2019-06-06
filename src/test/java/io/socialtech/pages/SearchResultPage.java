package io.socialtech.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class SearchResultPage extends AbstractPage {

    public SearchResultPage() {
        page(this);
    }

    @FindBy(xpath="//*[contains(@onclick,'goodsTitleClick')]")
    private SelenideElement first;

    public void openFirstProduct(){
        first.click();
    }

    public void selectCategory(String text) {
        $x("//li//*[contains(text(),'" + text + "')]").click();
    }

}
