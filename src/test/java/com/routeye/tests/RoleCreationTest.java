package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.RoleRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.role.AddRolePage;
import com.routeye.framework.pages.role.RoleListPage;
import org.testng.annotations.Test;

public class RoleCreationTest extends BaseTest {

    @Test(description = "Log in and create a new role under an existing client")
    public void createNewRole() {
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        new NavigationBar(driver).goToAccount();

        AddRolePage addRolePage = new RoleListPage(driver)
                .goToRolesTab()
                .clickAdd();

        RoleRequest request = new RoleRequest()
                .setClientName(ConfigReader.get("client.name"))
                .setTemplateName(ConfigReader.get("role.template"))
                .setDisplayName(ConfigReader.get("role.display.name"));

        addRolePage.fillAndSubmit(request);
    }
}
