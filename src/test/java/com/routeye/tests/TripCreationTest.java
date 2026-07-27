package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.TripRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.trip.TripCreationPage;
import com.routeye.framework.pages.trip.TripListPage;
import org.testng.annotations.Test;

public class TripCreationTest extends BaseTest {

    @Test(description = "End-to-end: log in, create a trip with device + alert configuration")
    public void createTripEndToEnd() {
        // 1. Log in - reuses a manually-captured session if SessionCapture.java
        //    has been run and it's still valid, otherwise logs in fresh
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        // 2. Navigate to Trips -> start a new trip
        new NavigationBar(driver).goToTrips();
        TripCreationPage tripCreation = new TripListPage(driver).clickCreateTrip();

        // 3. Build the test data for this run
        TripRequest request = new TripRequest()
                .setTripName(ConfigReader.get("trip.name"))
                .setOriginGeofenceSearchTerm(ConfigReader.get("trip.origin.search"))
                .setOriginGeofenceMatchText(ConfigReader.get("trip.origin.match"))
                .setDestinationGeofenceSearchTerm(ConfigReader.get("trip.destination.search"))
                .setDestinationGeofenceMatchText(ConfigReader.get("trip.destination.match"))
                .setDeviceSearchTerm(ConfigReader.get("trip.device.search"))
                .setDeviceMatchText(ConfigReader.get("trip.device.match"))
                .setPrimaryContactNumber(ConfigReader.get("trip.contact.primary"))
                .setSecondaryContactNumber(ConfigReader.get("trip.contact.secondary"))
                .setOverspeedLimitKmh(ConfigReader.get("trip.overspeed.kmh"))
                .setSmsAlertNumber(ConfigReader.get("trip.sms.number"))
                .setReceiptNumber(ConfigReader.get("trip.receipt.no"));

        // 4. Walk through the 4-step wizard
        tripCreation.selectTripAndLocations(request)
                .assignVehicle(request)
                .configureAlerts(request)
                .addPaymentDetailsAndCreateTrip(request);
    }
}
