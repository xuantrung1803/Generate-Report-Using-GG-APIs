package com.trung.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    public int TIME_IN_SECOND = Integer.parseInt(System.getProperty("TIME_IN_SECOND"));


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_IN_SECOND));
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public void navigate(String url) {
        driver.get(System.getProperty("BASE_URL") + url);
    }

    public void goToURL(String url) {
        driver.get(url);
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> waitForAllElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public Alert waitForAlertIsPresent() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public boolean isElementPresent(String nameElement) {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'%s')]".formatted(nameElement)));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void scrollDownDocument() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }

    public void inputText(By locator, String text) {
        WebElement element = waitForElementToBeClickable(locator);
        element.sendKeys(text);
    }

    public void clickElement(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
    }

    public void clickLinkText(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        element.click();
    }

    public String getText(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        return element.getText();
    }

    public String getValueAttribute(By locator) {
        WebElement element = waitForElementToBeVisible(locator);
        return element.getAttribute("value");
    }

    public void inputValueAttribute(String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('sliderValue').setAttribute('value', '" + text + "')");
        System.out.println("document.getElementById('sliderValue').setAttribute('value', '" + text + "')");
    }

    public void selectMultipleValueInDdl(By locator, By locatorElement, String values[]) {

        for (int i = 0; i < values.length; i++) {
            inputText(locator, values[i]);
            clickElement(locatorElement);
        }
    }

    public void selectMultipleValueInCheckbox(By locator, String values[]) {
        List<WebElement> elements = waitForAllElementsToBeVisible(locator);
        for (int i = 0; i < values.length; i++) {
            for (WebElement el : elements)
                if (el.getText().equals(values[i]))
                    el.click();
        }
    }

    public void SelectValueInDdlWithTagSelect(By locator, String value) {
        WebElement element = waitForElementToBeClickable(locator);
        Select selectSimple = new Select(element);
        selectSimple.selectByVisibleText(value);
    }

    public String configDataString(String values[]) {
        String stringConcatenation = values[0];
        for (int i = 1; i < values.length; i++) {
            stringConcatenation += ", " + values[i];
        }
        return stringConcatenation;

    }

    public void zoomDocument(int number) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String s = "document.body.style.zoom='" + String.valueOf(number) + "%';";
        jse.executeScript(s);
    }

    public void zoomOutScreen() throws AWTException {
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        for (int i = 0; i <= 3; ++i) {
            rb.keyPress(KeyEvent.VK_MINUS);
            rb.keyRelease(KeyEvent.VK_A);
        }
        rb.keyRelease(KeyEvent.VK_CONTROL);
    }

    public ArrayList<String> getDataInTBL(By locatorTable) {
        List<WebElement> elements = waitForAllElementsToBeVisible(locatorTable);
        ArrayList<String> data = new ArrayList<String>();
        for (WebElement el : elements) {
            data.add(el.getText());
        }
        return data;
    }

    public void dragToDrop(By locator, int x, int y) {
        Actions action = new Actions(driver);
        WebElement element = waitForElementToBeClickable(locator);
        action.dragAndDropBy(element, x, y).build().perform();
    }

    public void selectMonthAndYear(By locatorMonth, By locateYear, By previousBtn, String month, String year) {
        while (true) {
            String currentMonth = getText(locatorMonth);
            String currentYear = getText(locateYear);
            if (currentMonth.equals(month) && currentYear.equals(year))
                break;
            clickElement(previousBtn);
        }
    }

    public void selectDay(By locator, String date) {
        List<WebElement> listDate = findElements(locator);
        for (WebElement currentDate : listDate)
            if (currentDate.getText().equals(date)) {
                currentDate.click();
                break;
            }


    }

}
