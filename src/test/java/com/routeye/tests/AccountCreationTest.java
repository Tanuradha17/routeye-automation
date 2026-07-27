package com.routeye.tests;

import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.models.AccountRequest;
import com.routeye.framework.pages.LoginPage;
import com.routeye.framework.pages.NavigationBar;
import com.routeye.framework.pages.account.AccountListPage;
import com.routeye.framework.pages.account.AddAccountPage;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountCreationTest extends BaseTest {

    private static final DateTimeFormatter DATETIME_LOCAL_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Test(description = "Log in and create a new account under an existing client")
    public void createNewAccount() {
        new LoginPage(driver).loginUsingSavedSessionOrCredentials(
                ConfigReader.get("test.username"), ConfigReader.get("test.password"));

        new NavigationBar(driver).goToAccount();

        AddAccountPage addAccountPage = new AccountListPage(driver).clickAdd();

        // Validity Start Time must be after "now" - compute it fresh each run.
        // Validity End Time fixed to 30 July 2026, 6:00 PM per test requirement.
        String validityStart = LocalDateTime.now().plusDays(1).format(DATETIME_LOCAL_FORMAT);
        String validityEnd = LocalDateTime.of(2026, 7, 30, 18, 0).format(DATETIME_LOCAL_FORMAT);

        AccountRequest request = new AccountRequest()
                .setClientName(ConfigReader.get("client.name"))
                .setAccountName(ConfigReader.get("account.name"))
                .setContactName(ConfigReader.get("account.contact.name"))
                .setContactNo(ConfigReader.get("account.contact.no"))
                .setStateName(ConfigReader.get("account.state"))
                .setCityName(ConfigReader.get("account.city"))
                .setValidityStartTime(validityStart)
                .setValidityEndTime(validityEnd);

        addAccountPage.fillAndSubmit(request);
    }
}