package com.routeye.framework.pages.client;

import com.routeye.framework.models.ClientRequest;
import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * routeye.com/settings/clients/add - the Add Client form.
 *
 * All text-input ids on this form (e.g. id="input-2y475ckcw") are randomly
 * generated per page load - confirmed by inspecting the same field twice
 * and seeing the id change - so every locator here is placeholder-based
 * instead.
 *
 * State Name and Status are custom comboboxes (role="combobox") with no
 * search box, unlike the geofence/device pickers in TripCreationPage -
 * handled via BasePage.selectFromDropdown() rather than
 * selectFromSearchableDropdown().
 *
 * Required fields: Client Name, Contact Name, Contact No, State Name,
 * City Name, Status. Optional (skipped if blank on the request): Email,
 * Pan Number, Aadhar Number, GST Number, Remark.
 */
public class AddClientPage extends BasePage {

    private final By clientNameInput   = By.cssSelector("input[placeholder='Enter client name']");
    private final By contactNameInput  = By.cssSelector("input[placeholder='Enter contact name']");
    private final By emailInput        = By.cssSelector("input[placeholder='Enter email address']");
    private final By contactNoInput    = By.cssSelector("input[placeholder='Enter contact number']");
    private final By panNumberInput    = By.cssSelector("input[placeholder='ABCDE1234F']");
    private final By aadharNumberInput = By.cssSelector("input[placeholder='123456789012']");
    private final By gstNumberInput    = By.cssSelector("input[placeholder='22AAAAA0000A1Z5']");
    private final By cityNameInput     = By.cssSelector("input[placeholder='Enter city name']");

    private final By stateCombobox =
            By.xpath("//span[normalize-space()='Select State']/ancestor::div[@role='combobox']");
    private final By statusCombobox =
            By.xpath("//span[normalize-space()='Select Status']/ancestor::div[@role='combobox']");

    private final By saveButton = By.xpath("//button[normalize-space()='Save']");

    public AddClientPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills every field on the Add Client form from the given request and
     * clicks Save. Optional fields are only typed if present on the request.
     */
    public void fillAndSubmit(ClientRequest request) {
        type(clientNameInput, request.getClientName());
        type(contactNameInput, request.getContactName());
        typeIfPresent(emailInput, request.getEmail());
        type(contactNoInput, request.getContactNo());
        typeIfPresent(panNumberInput, request.getPanNumber());
        typeIfPresent(aadharNumberInput, request.getAadharNumber());
        typeIfPresent(gstNumberInput, request.getGstNumber());

        selectFromSearchableDropdown(stateCombobox, request.getStateName(), request.getStateName());
        type(cityNameInput, request.getCityName());
        selectFromDropdown(statusCombobox, request.getStatus());

        // Remark intentionally skipped - optional field, no locator captured yet

        clickSave();
    }

    private void typeIfPresent(By locator, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        type(locator, value);
    }

    /**
     * Save is disabled (via the `disabled` attribute) until all required
     * fields pass validation - click(By) already waits for clickability,
     * so this naturally waits for that too.
     */
    public void clickSave() {
        click(saveButton);
    }
}