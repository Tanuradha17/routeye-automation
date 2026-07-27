package com.routeye.framework.pages.user;

import com.routeye.framework.models.UserRequest;
import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddUserPage extends BasePage {

    private final By clientCombobox =
            By.xpath("//span[normalize-space()='Select Client']/ancestor::div[@role='combobox']");

    private final By accountOrGroupCombobox =
            By.xpath("//span[normalize-space()='Select Account or Group']/ancestor::div[@role='combobox']");

    private final By accountNameCombobox =
            By.xpath("//span[normalize-space()='Select Account']/ancestor::div[@role='combobox']");

    private final By roleCombobox =
            By.xpath("//span[normalize-space()='Select Role']/ancestor::div[@role='combobox']");

    private final By usernameInput  = By.cssSelector("input[placeholder='Enter username']");
    private final By firstNameInput = By.cssSelector("input[placeholder='Enter first name']");
    private final By lastNameInput  = By.cssSelector("input[placeholder='Enter last name']");
    private final By contactNoInput = By.cssSelector("input[placeholder='Enter contact number']");
    private final By passwordInput  = By.cssSelector("input[placeholder='Enter password']");

    private final By saveButton = By.xpath("//button[normalize-space()='Save']");

    public AddUserPage(WebDriver driver) {
        super(driver);
    }

    public void fillAndSubmit(UserRequest request) {
        selectFromSearchableDropdown(clientCombobox, request.getClientName(), request.getClientName());

        selectFromDropdown(accountOrGroupCombobox, request.getAccountOrGroupType());

        selectFromSearchableDropdown(accountNameCombobox, request.getAccountName(), request.getAccountName());
        selectFromDropdown(roleCombobox, request.getRoleName());

        type(usernameInput, request.getUsername());
        type(firstNameInput, request.getFirstName());
        type(lastNameInput, request.getLastName());
        type(contactNoInput, request.getContactNo());
        type(passwordInput, request.getPassword());

        clickSave();
    }

    public void clickSave() {
        click(saveButton);
    }
}