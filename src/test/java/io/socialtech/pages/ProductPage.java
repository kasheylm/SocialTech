package io.socialtech.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class ProductPage extends AbstractPage {

    public ProductPage() {
        page(this);
    }

    @FindBy(xpath="//*[contains(@href,'characteristics')]")
    private SelenideElement detailsTab;

    public void openProductDetails(){
        detailsTab.click();
    }
}
