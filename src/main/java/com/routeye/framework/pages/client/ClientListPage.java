package com.routeye.framework.pages.client;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClientListPage extends BasePage {

    private final By clientsTab = By.cssSelector("div[role='tab'][data-tab-label='Clients']");
    private final By addButton = By.xpath("//button[normalize-space()='+ Add']");

    public ClientListPage(WebDriver driver) {
        super(driver);
    }

    public ClientListPage goToClientsTab() {
        click(clientsTab);
        return this;
    }

    public AddClientPage clickAdd() {
        click(addButton);
        return new AddClientPage(driver);
    }
}