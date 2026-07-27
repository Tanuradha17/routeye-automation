package com.routeye.framework.pages.trip;

import com.routeye.framework.models.TripRequest;
import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Models the "Create New Trip" wizard (steps 01-04 shown in the UI):
 *   01 Trip & locations configure
 *   02 Vehicle & Asset Assignment
 *   03 Trip Alert configuration
 *   04 Payments & documents
 *
 * Each step is one method that takes the same TripRequest, so a test reads
 * as a straight-line story instead of 40 lines of findElement calls.
 */
public class TripCreationPage extends BasePage {

    // ---- Step 1: Trip & locations configure ----
    private final By tripTypeIcon = By.xpath("//div[@class='flex items-center gap-1 ml-2']//*[name()='svg']");
    private final By originGeofenceCombobox =
            By.xpath("//span[normalize-space()='Select origin geofence']/ancestor::div[@role='combobox']");
    private final By destinationGeofenceCombobox =
            By.xpath("//span[normalize-space()='Select destination geofence']/ancestor::div[@role='combobox']");
    private final By getRouteButton = By.xpath("//button[normalize-space()='Get Route']");
    private final By selectRouteDropdown = By.xpath("//span[normalize-space()='Select Route']");
    private final By firstRouteOption = By.xpath("//span[contains(@class,'flex-1 truncate')]");
    private final By nextButton = By.xpath("//button[normalize-space()='Next']");

    // ---- Step 2: Vehicle & Asset Assignment ----
    private final By primaryDeviceCombobox =
            By.xpath("//span[normalize-space()='Select Primary Device']/ancestor::div[@role='combobox']");

    // ---- Step 3: Trip Alert configuration ----
    private final By primaryContactField =
            By.xpath("//input[contains(@placeholder,'Enter Mobile Numbers with comma(,)')][1]");
    // TODO: positional locator carried over from the original script - fragile.
    // Ask a dev for a data-testid/name/id on this field and replace it.
    private final By secondaryContactField = By.xpath("//div[6]//div[2]//input[1]");
    private final By overspeedButton = By.xpath("//button[normalize-space()='Overspeed']");
    private final By speedField = By.xpath("//input[@placeholder='Enter Speed in Km/hr']");
    private final By smsCheckbox = By.id("sms");
    private final By smsMobileField = By.xpath("//input[@placeholder='Enter Mobile Numbers']");

    // ---- Step 4: Payments & documents ----
    private final By receiptNoField = By.xpath("//input[@placeholder='Enter Receipt No']");
    private final By createTripButton = By.xpath("//button[normalize-space()='Create Trip']");

    public TripCreationPage(WebDriver driver) {
        super(driver);
    }

    public TripCreationPage selectTripAndLocations(TripRequest request) {
        click(tripTypeIcon);
        click(By.xpath("//span[normalize-space()='" + request.getTripName() + "']"));

        selectFromSearchableDropdown(originGeofenceCombobox,
                request.getOriginGeofenceSearchTerm(), request.getOriginGeofenceMatchText());
        selectFromSearchableDropdown(destinationGeofenceCombobox,
                request.getDestinationGeofenceSearchTerm(), request.getDestinationGeofenceMatchText());

        click(getRouteButton);
        click(selectRouteDropdown);
        click(firstRouteOption);

        clickNext();
        return this;
    }

    public TripCreationPage assignVehicle(TripRequest request) {
        selectFromSearchableDropdown(primaryDeviceCombobox,
                request.getDeviceSearchTerm(), request.getDeviceMatchText());

        // Confirm the chosen device's IMEI actually rendered before moving on
        waitUtils.waitForVisible(By.xpath("//span[contains(text(),'" + request.getDeviceMatchText() + "')]"));

        clickNext();
        return this;
    }

    public TripCreationPage configureAlerts(TripRequest request) {
        typeWithoutClear(primaryContactField, request.getPrimaryContactNumber());
        typeWithoutClear(secondaryContactField, request.getSecondaryContactNumber());
        clickNext();

        click(overspeedButton);
        WebElement speed = waitUtils.waitForVisible(speedField);
        speed.sendKeys(request.getOverspeedLimitKmh());

        click(smsCheckbox);
        typeWithoutClear(smsMobileField, request.getSmsAlertNumber());
        clickNext();
        return this;
    }

    public void addPaymentDetailsAndCreateTrip(TripRequest request) {
        typeWithoutClear(receiptNoField, request.getReceiptNumber());
        click(createTripButton);
    }

    private void clickNext() {
        click(nextButton);
    }
}
