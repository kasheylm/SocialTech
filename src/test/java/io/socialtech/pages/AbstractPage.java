package io.socialtech.pages;

import com.codeborne.selenide.Configuration;

public abstract class AbstractPage {

    public AbstractPage() {
        Configuration.browser = "chrome";
        Configuration.timeout = 5000;
    }
}
