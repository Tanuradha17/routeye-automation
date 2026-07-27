package com.routeye.framework.pages.role;

import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Account -> Roles tab: the page landed on before clicking "+ Add".
 * TODO: confirm goToRolesTab() navigation - is Roles a tab clicked after
 * NavigationBar.goToAccount(), or a separate top-level nav link?
 */
public class RoleListPage extends BasePage {

    private final By rolesTab = By.cssSelector("div[role='tab'][data-tab-label='Roles']");

    // TODO: placeholder - confirm actual "+ Add" button HTML for Roles list
    private final By addButton = By.xpath("//button[normalize-space()='+ Add']");

    public RoleListPage(WebDriver driver) {
        super(driver);
    }

    public RoleListPage goToRolesTab() {
        click(rolesTab);
        return this;
    }

    public AddRolePage clickAdd() {
        click(addButton);
        return new AddRolePage(driver);
    }
}