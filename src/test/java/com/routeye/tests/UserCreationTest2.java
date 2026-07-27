package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.UserRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.user.AddUserPage;
import com.routeye.framework.pages.user.UserListPage;
import org.testng.annotations.Test;

public class UserCreationTest2 extends BaseTest {

    @Test(description = "Log in and create a new user under an existing client, account, and role")
    public void createNewUser() {
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        new NavigationBar(driver).goToAccount();

        AddUserPage addUserPage = new UserListPage(driver)
                .goToUsersTab()
                .clickAdd();

        UserRequest request = new UserRequest()
                .setClientName(ConfigReader.get("client.name"))
                .setAccountOrGroupType(ConfigReader.get("user.account.type"))
                .setAccountName(ConfigReader.get("account.name"))
                .setRoleName(ConfigReader.get("role.template"))
                .setUsername(ConfigReader.get("user.username"))
                .setFirstName(ConfigReader.get("user.first.name"))
                .setLastName(ConfigReader.get("user.last.name"))
                .setContactNo(ConfigReader.get("user.contact.no"))
                .setPassword(ConfigReader.get("user.password"));

        addUserPage.fillAndSubmit(request);
    }
}