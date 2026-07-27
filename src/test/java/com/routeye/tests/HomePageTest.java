package com.routeye.tests;

import com.routeye.framework.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test(description = "Home page should load with the correct title")
    public void homePageLoadsSuccessfully() {
        HomePage home = new HomePage(driver).load();
        home.dismissCookieBannerIfPresent();

        Assert.assertTrue(home.getTitle().length() > 0, "Page title should not be empty");
        Assert.assertTrue(home.getCurrentUrl().contains("routeye.com"),
                "URL should contain routeye.com");
    }

    @Test(description = "Logo and nav menu should be visible on the home page")
    public void homePageCoreElementsAreVisible() {
        HomePage home = new HomePage(driver).load();
        home.dismissCookieBannerIfPresent();

        Assert.assertTrue(home.isLogoDisplayed(), "Logo should be visible");
        Assert.assertTrue(home.isNavMenuDisplayed(), "Nav menu should be visible");
    }
}
