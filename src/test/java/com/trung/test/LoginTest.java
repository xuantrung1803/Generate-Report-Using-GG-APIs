package com.trung.test;

import com.trung.pages.LoginPage;
import com.trung.pages.NavigationPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginInWithValidAccount() {
        loginPage.loginWithValidAccount(System.getProperty("USERNAME"), System.getProperty("PASSWORD"));
        assertThat("Verify visible userName: ", loginPage.getAppLogo(), equalTo("Swag Labs"));
    }

    @Test
    public void loginInWithInValidAccount() {
        loginPage.loginWithValidAccount(System.getProperty("USERNAME"), System.getProperty("INVALID_PASSWORD"));
        assertThat("Verify visible userName: ", loginPage.getErrorMessage(), equalTo("Epic sadface: Username and password do not match any user in this service"));
    }
}

