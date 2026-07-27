package com.routeye.framework.models;

/**
 * Data needed to create one role via the Add Role form.
 * clientName ties the role to an existing client (same pattern as Account).
 * templateName picks a starting template (e.g. "Super Admin") which
 * auto-fills permissions - confirm exact behavior once template dropdown
 * structure is captured.
 */
public class RoleRequest {

    private String clientName;
    private String templateName;
    private String displayName;

    public String getClientName() {
        return clientName;
    }

    public RoleRequest setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public RoleRequest setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RoleRequest setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
