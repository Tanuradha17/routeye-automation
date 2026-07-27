package com.routeye.framework.pages.trip;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TripListPage extends BasePage {

    private final By createTripLink = By.xpath("//span[normalize-space()='Create Trip']");

    public TripListPage(WebDriver driver) {
        super(driver);
    }

    /** @return a fresh TripCreationPage, since clicking this opens the wizard */
    public TripCreationPage clickCreateTrip() {
        click(createTripLink);
        return new TripCreationPage(driver);
    }
}
