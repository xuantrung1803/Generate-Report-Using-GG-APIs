package com.trung.test;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.trung.helper.CustomExtentListener;
import com.trung.utils.ConfigFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.FileNotFoundException;
import java.util.Properties;

@Listeners(CustomExtentListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeSuite
    public void beforeSuite() throws FileNotFoundException {
        Properties properties = ConfigFileReader.loadPropertiesFromFile(System.getProperty("env.properties"));
        ConfigFileReader.appendSystemProperties(properties);
    }

    @BeforeMethod
    public void beforeMethodBaseTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

}
