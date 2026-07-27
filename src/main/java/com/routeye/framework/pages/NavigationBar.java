package com.routeye.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationBar extends BasePage {

    private final By tripsLink = By.xpath("//a[@data-tooltip-content='TRIPS']");

    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public void goToTrips() {
        click(tripsLink);
    }


private final By settingsLink = By.cssSelector("a[href='/settings']");

private final By accountLink = By.cssSelector("a[href='/account']");

public void goToSettings() {
    click(settingsLink);
}

public void goToAccount() {
    click(accountLink);
}
}