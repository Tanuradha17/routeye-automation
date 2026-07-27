package com.routeye.framework.pages.user;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserListPage extends BasePage {

    private final By usersTab = By.cssSelector("div[role='tab'][data-tab-label='Users']");
    private final By addButton = By.xpath("//button[normalize-space()='+ Add']");

    public UserListPage(WebDriver driver) {
        super(driver);
    }

    public UserListPage goToUsersTab() {
        click(usersTab);
        return this;
    }

    public AddUserPage clickAdd() {
        click(addButton);
        return new AddUserPage(driver);
    }
}