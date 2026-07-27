package com.routeye.framework.models;

public class ClientRequest {

    private String clientName;
    private String contactName;
    private String email;
    private String contactNo;
    private String panNumber;
    private String aadharNumber;
    private String gstNumber;
    private String stateName;
    private String cityName;
    private String status;
    private String remark;

    public String getClientName() {
        return clientName;
    }

    public ClientRequest setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public ClientRequest setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContactNo() {
        return contactNo;
    }

    public ClientRequest setContactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public ClientRequest setPanNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public ClientRequest setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
        return this;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public ClientRequest setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public ClientRequest setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public ClientRequest setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ClientRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ClientRequest setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}