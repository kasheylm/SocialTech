package io.socialtech.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.page;

public class ProductDetailsPage extends AbstractPage {

    public static final int MINIMUM_TABLE_SIZE = 25;

    public ProductDetailsPage() {
        page(this);
    }

    private Map<String, String> details = new HashMap<>();

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @FindBy(xpath="//tr//td[contains(@class, \"feature-t-cell\")]")
    private ElementsCollection table;

    @FindBy(xpath="//td[contains(@class,'feature-t-cell')][1]")
    private ElementsCollection property;

    @FindBy(xpath="//td[contains(@class,'feature-t-cell')][2]")
    private ElementsCollection value;

    public void getAllInfo(){
        table.shouldHave(CollectionCondition.sizeGreaterThan(MINIMUM_TABLE_SIZE));
        HashMap<String, String> info = new HashMap<>();
        for (int i = 0; i < property.size() ; i++) {
            info.put(property.get(i).text(), value.get(i).text());
        }
        setDetails(info);
    }

    public String getValueByProperty(String property){
        return getDetails().get(property);
    }
}
