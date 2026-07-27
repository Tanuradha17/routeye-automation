package com.routeye.framework.pages;

import com.routeye.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Every Page Object extends this. Keeps common, reusable Selenium actions
 * in one place instead of duplicating driver.findElement(...) everywhere.
 */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WaitUtils waitUtils;
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        // Enables @FindBy annotations in subclasses
        PageFactory.initElements(driver, this);
    }
    protected void click(By locator) {
        int attempts = 3;
        for (int i = 1; i <= attempts; i++) {
            try {
                waitUtils.waitForClickable(locator).click();
                return;
            } catch (StaleElementReferenceException e) {
                if (i == attempts) {
                    throw e;
                }
                // Element was replaced by a re-render between locating and
                // clicking - re-locate fresh and retry, up to `attempts` times.
            }
        }
    }
    protected void click(WebElement element) {
        element.click();
    }
    protected void type(By locator, String text) {
        WebElement el = waitUtils.waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }
    protected String getText(By locator) {
        return waitUtils.waitForVisible(locator).getText();
    }
    /**
     * Types into a field without clearing it first (useful when the field is
     * meant to accept comma-separated additions, e.g. "Enter Mobile Numbers with comma(,)").
     */
    protected void typeWithoutClear(By locator, String text) {
        waitUtils.waitForVisible(locator).sendKeys(text);
    }
    /**
     * Handles the "searchable combobox" pattern used all over Routeye
     * (device pickers, geofence pickers, route pickers, etc.):
     *   1. click the combobox to open it
     *   2. type a search term into the options filter
     *   3. click the option whose text contains the given match text
     *
     * @param comboboxTrigger element that opens the dropdown when clicked
     * @param searchTerm      text typed into the "Search options..." box
     * @param optionMatchText visible text (or partial text) of the option to select
     */
    protected void selectFromSearchableDropdown(By comboboxTrigger, String searchTerm, String optionMatchText) {
        click(comboboxTrigger);
        By searchBox = By.xpath("//input[@placeholder='Search options...']");
        waitUtils.waitForVisible(searchBox).sendKeys(searchTerm);
        By matchingOption = By.xpath(
                "//span[contains(normalize-space(),'" + optionMatchText + "')]");
        click(matchingOption);
    }
    protected boolean isDisplayed(By locator) {
        try {
            return waitUtils.waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public String getTitle() {
        return driver.getTitle();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    public void open(String url) {
        driver.get(url);
    }
    /**
     * Handles the plain "click combobox -> click option" pattern, used by
     * comboboxes that have no search box (e.g. State Name, Status on the
     * Add Client form) - as opposed to selectFromSearchableDropdown above,
     * which types a search term first.
     *
     * @param comboboxTrigger element that opens the dropdown when clicked
     * @param optionMatchText visible text (or partial text) of the option to select
     */
    protected void selectFromDropdown(By comboboxTrigger, String optionMatchText) {
        click(comboboxTrigger);
        By matchingOption = By.xpath(
                "//span[contains(@class,'flex-1 truncate') and normalize-space()='" + optionMatchText + "']");
        click(matchingOption);
    }
    protected void setDateTimeValue(By locator, String value) {
        WebElement el = waitUtils.waitForVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                        + "nativeSetter.call(arguments[0], arguments[1]);"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                el, value);
    }
}