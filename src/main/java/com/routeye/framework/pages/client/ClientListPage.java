package com.routeye.framework.pages.client;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    /** Returns true if a client with this exact name appears in the list. */
    public boolean isClientPresent(String clientName) {
        By clientRow = By.xpath("//*[normalize-space(text())='" + clientName + "']");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(clientRow));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}