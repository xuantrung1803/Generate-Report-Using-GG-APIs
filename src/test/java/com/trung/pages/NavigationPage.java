package com.trung.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class NavigationPage extends BasePage {
    private final By LBL_USERNAME = By.id("userName-value");

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public String getUserName() {
        return getText(LBL_USERNAME);
    }

    public boolean isEnableUserName() {
        WebElement element = waitForElementToBeClickable(LBL_USERNAME);
        if (element != null)
            return true;
        return false;
    }
}
