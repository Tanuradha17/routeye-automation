package com.routeye.framework.pages.account;

import com.routeye.framework.models.AccountRequest;
import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddAccountPage extends BasePage {

    private final By clientNameCombobox =
            By.xpath("//span[normalize-space()='Select Client Name']/ancestor::div[@role='combobox']");

    private final By accountNameInput = By.cssSelector("input[placeholder='Enter account name']");
    private final By contactNameInput = By.cssSelector("input[placeholder='Enter contact name']");
    private final By contactNoInput   = By.cssSelector("input[placeholder='Enter contact number']");

    private final By stateCombobox =
            By.xpath("//span[normalize-space()='Select State']/ancestor::div[@role='combobox']");
    private final By cityNameInput = By.cssSelector("input[placeholder='Enter city name']");

    private final By validityStartInput =
            By.xpath("//label[contains(text(),'Validity Start Time')]/following::input[1]");
    private final By validityEndInput =
            By.xpath("//label[contains(text(),'Validity End Time')]/following::input[1]");

    private final By saveButton = By.xpath("//button[normalize-space()='Save']");

    public AddAccountPage(WebDriver driver) {
        super(driver);
    }

    public void fillAndSubmit(AccountRequest request) {
        selectFromSearchableDropdown(clientNameCombobox, request.getClientName(), request.getClientName());

        type(accountNameInput, request.getAccountName());
        type(contactNameInput, request.getContactName());
        type(contactNoInput, request.getContactNo());

        selectFromSearchableDropdown(stateCombobox, request.getStateName(), request.getStateName());
        type(cityNameInput, request.getCityName());

        setDateTimeValue(validityStartInput, request.getValidityStartTime());
        setDateTimeValue(validityEndInput, request.getValidityEndTime());


        clickSave();
    }

    public void clickSave() {
        click(saveButton);
    }
}