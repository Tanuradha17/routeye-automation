package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.ClientRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.client.AddClientPage;
import com.routeye.framework.pages.client.ClientListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClientCreationTest extends BaseTest {

    @Test(description = "Log in and create a new client via Settings -> Clients -> Add")
    public void createNewClient() {
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        new NavigationBar(driver).goToSettings();

        ClientListPage clientListPage = new ClientListPage(driver);
        AddClientPage addClientPage = clientListPage
                .goToClientsTab()
                .clickAdd();

        // Unique name on every run. Only letters, numbers, spaces, hyphens and dots
        // are allowed by the app, so use a hyphen (NOT an underscore) as the separator.
        String uniqueName = ConfigReader.get("client.name") + "-" + System.currentTimeMillis();

        // Unique 10-digit mobile number on every run (starts with 9, valid Indian format)
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueContactNo = "9" + timestamp.substring(timestamp.length() - 9);

        ClientRequest request = new ClientRequest()
                .setClientName(uniqueName)
                .setContactName(ConfigReader.get("client.contact.name"))
                .setContactNo(uniqueContactNo)
                .setStateName(ConfigReader.get("client.state"))
                .setCityName(ConfigReader.get("client.city"))
                .setStatus(ConfigReader.get("client.status"));

        addClientPage.fillAndSubmit(request);

        // Reload the Clients list, then verify the new client really exists
        clientListPage.goToClientsTab();
        Assert.assertTrue(clientListPage.isClientPresent(uniqueName),
                "Client was not created: " + uniqueName);
    }
}