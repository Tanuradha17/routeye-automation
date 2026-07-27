package com.routeye.framework.pages.role;

import com.routeye.framework.models.RoleRequest;
import com.routeye.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * uat.routeye.com/account/roles/add - the Add Role form.
 *
 * Client Name is a searchable combobox (same pattern as State/Client Name
 * on other forms). The role name/template field opens a template picker
 * panel just by clicking into it (no typing needed) - selecting a template
 * (e.g. "Superadmin") auto-fills the module permissions matrix, so that
 * matrix is never touched directly here.
 *
 * Status is pre-filled "Active" by default - left untouched, same as
 * Account's Status field.
 */
public class AddRolePage extends BasePage {

    private final By clientCombobox =
            By.xpath("//span[normalize-space()='Select client']/ancestor::div[@role='combobox']");

    private final By roleNameOrTemplateInput =
            By.cssSelector("input[placeholder='Enter role name or choose a template below']");

    private final By superadminTemplateCard = By.xpath("//span[normalize-space()='Superadmin']");

    private final By displayNameInput = By.cssSelector("input[placeholder='Enter display name']");

    private final By createRoleButton = By.xpath("//button[normalize-space()='Create Role']");

    public AddRolePage(WebDriver driver) {
        super(driver);
    }

    public void fillAndSubmit(RoleRequest request) {
        selectFromSearchableDropdown(clientCombobox, request.getClientName(), request.getClientName());

        click(roleNameOrTemplateInput); // focuses the field, opens the template panel
        click(superadminTemplateCard);  // TODO: generalize to any template once more than one is needed

        type(displayNameInput, request.getDisplayName());

        clickCreateRole();
    }

    public void clickCreateRole() {
        click(createRoleButton);
    }
}