package com.routeye.framework.pages;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.utils.CookieManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final String COOKIE_FILE = "src/test/resources/session-cookies.dat";

    private final By usernameField = By.xpath("//input[@placeholder='Username or email address']");
    private final By passwordField = By.xpath("//input[@placeholder='••••••••••••••']");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage load() {
        open(ConfigReader.get("base.url") + "/auth/login");
        return this;
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(submitButton);
    }

    /**
     * Tries to restore a session saved earlier by SessionCapture (a session a
     * human already authenticated, cookies and all). Falls back to a normal
     * credential login only if no valid saved session is found or it's expired.
     *
     * @return true if the saved session was used, false if it fell back to
     *         a fresh credential login
     */
    public boolean loginUsingSavedSessionOrCredentials(String username, String password) {
        open(ConfigReader.get("base.url")); // must be on the domain before cookies can be added

        boolean cookiesLoaded = CookieManager.loadCookies(driver, COOKIE_FILE);
        if (cookiesLoaded) {
            driver.navigate().refresh();
            if (!getCurrentUrl().contains("/auth/login")) {
                return true; // saved session is still valid, we're already in
            }
            System.out.println("Saved session cookies are expired or invalid - "
                    + "re-run SessionCapture.java to refresh them. Falling back to normal login.");
        }

        load();
        login(username, password);
        return false;
    }
}