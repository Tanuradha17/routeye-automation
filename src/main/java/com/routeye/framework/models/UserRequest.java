package com.routeye.framework.models;

public class UserRequest {

    private String clientName;
    private String accountOrGroupType;
    private String accountName;
    private String roleName;
    private String username;
    private String firstName;
    private String lastName;
    private String contactNo;
    private String password;

    public String getClientName() {
        return clientName;
    }

    public UserRequest setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getAccountOrGroupType() {
        return accountOrGroupType;
    }

    public UserRequest setAccountOrGroupType(String accountOrGroupType) {
        this.accountOrGroupType = accountOrGroupType;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public UserRequest setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public UserRequest setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getContactNo() {
        return contactNo;
    }

    public UserRequest setContactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
