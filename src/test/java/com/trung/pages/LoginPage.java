package com.trung.pages;

import com.trung.constants.UrlConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By TXT_USERNAME = By.id("user-name");
    private final By TXT_PASSWORD = By.id("password");
    private final By BTN_LOGIN = By.id("login-button");
    private final By LBL_ERROR = By.xpath("//h3[@data-test='error']");
    private final By LBL_LOGOAPP = By.className("app_logo");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void inputUsername(String userName) {
        inputText(TXT_USERNAME, userName);
    }

    public void inputPassword(String password) {
        inputText(TXT_PASSWORD, password);
    }

    public void clickLoginBtn() {
        clickElement(BTN_LOGIN);
    }

    public String getErrorMessage() {
        return getText(LBL_ERROR);
    }

    public String getAppLogo() {
        return getText(LBL_LOGOAPP);
    }

    public void loginWithValidAccount(String username, String password) {
        navigate(UrlConstants.LOGIN_URL);
        inputUsername(username);
        inputPassword(password);
        clickLoginBtn();
    }
}
