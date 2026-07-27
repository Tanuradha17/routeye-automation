package com.routeye.framework.pages;

import com.routeye.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the routeye.com home page.
 *
 * NOTE: I could not load routeye.com directly to inspect its real DOM, so the
 * locators below are PLACEHOLDERS. Replace them with real ones from your app:
 *   1. Open https://routeye.com in Chrome
 *   2. Right-click the element -> Inspect
 *   3. Prefer stable attributes: data-testid, id, name > css/xpath tied to text or position
 */
public class HomePage extends BasePage {

    // ---- Locators (UPDATE THESE to match the real page) ----
    private final By logo = By.cssSelector("header .logo, a.navbar-brand");
    private final By navMenu = By.cssSelector("nav");
    private final By loginButton = By.xpath("//a[contains(text(),'Login') or contains(text(),'Sign in')]");
    private final By getStartedButton = By.xpath("//a[contains(text(),'Get Started') or contains(text(),'Sign up')]");
    private final By cookieBannerAccept = By.xpath("//button[contains(text(),'Accept')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage load() {
    	open(ConfigReader.get("base.url"));        
    	return this;
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(logo);
    }

    public boolean isNavMenuDisplayed() {
        return isDisplayed(navMenu);
    }

    public void dismissCookieBannerIfPresent() {
        if (isDisplayed(cookieBannerAccept)) {
            click(cookieBannerAccept);
        }
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void clickGetStarted() {
        click(getStartedButton);
    }
}
