package com.routeye.framework.models;

public class AccountRequest {

    private String clientName;
    private String parentAccountName;
    private String accountName;
    private String contactName;
    private String contactNo;
    private String stateName;
    private String cityName;
    private String validityStartTime;
    private String validityEndTime;

    public String getClientName() {
        return clientName;
    }

    public AccountRequest setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getParentAccountName() {
        return parentAccountName;
    }

    public AccountRequest setParentAccountName(String parentAccountName) {
        this.parentAccountName = parentAccountName;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public AccountRequest setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public AccountRequest setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getContactNo() {
        return contactNo;
    }

    public AccountRequest setContactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public AccountRequest setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public AccountRequest setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getValidityStartTime() {
        return validityStartTime;
    }

    public AccountRequest setValidityStartTime(String validityStartTime) {
        this.validityStartTime = validityStartTime;
        return this;
    }

    public String getValidityEndTime() {
        return validityEndTime;
    }

    public AccountRequest setValidityEndTime(String validityEndTime) {
        this.validityEndTime = validityEndTime;
        return this;
    }
}
