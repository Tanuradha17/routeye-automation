package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.ClientRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.client.AddClientPage;
import com.routeye.framework.pages.client.ClientListPage;
import org.testng.annotations.Test;

public class ClientCreationTest extends BaseTest {

    @Test(description = "Log in and create a new client via Settings -> Clients -> Add")
    public void createNewClient() {
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        new NavigationBar(driver).goToSettings();

        AddClientPage addClientPage = new ClientListPage(driver)
                .goToClientsTab()
                .clickAdd();

        ClientRequest request = new ClientRequest()
                .setClientName(ConfigReader.get("client.name"))
                .setContactName(ConfigReader.get("client.contact.name"))
                .setContactNo(ConfigReader.get("client.contact.no"))
                .setStateName(ConfigReader.get("client.state"))
                .setCityName(ConfigReader.get("client.city"))
                .setStatus(ConfigReader.get("client.status"));

        addClientPage.fillAndSubmit(request);
    }
}