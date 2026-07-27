package com.routeye.framework.pages.account;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountListPage extends BasePage {

    private final By addButton = By.xpath("//button[normalize-space()='+ Add']");

    public AccountListPage(WebDriver driver) {
        super(driver);
    }

    public AddAccountPage clickAdd() {
        click(addButton);
        return new AddAccountPage(driver);
    }
}