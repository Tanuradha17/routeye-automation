package com.routeye.framework.models;

/**
 * All the input data needed to create one trip end-to-end.
 * Keeping this separate from the test class means the same trip flow
 * can be re-run with different data (different trips, devices, numbers)
 * without touching TripCreationPage or the test logic at all.
 */
public class TripRequest {

    private String tripName;
    private String originGeofenceSearchTerm;
    private String originGeofenceMatchText;
    private String destinationGeofenceSearchTerm;
    private String destinationGeofenceMatchText;
    private String deviceSearchTerm;
    private String deviceMatchText;
    private String primaryContactNumber;
    private String secondaryContactNumber;
    private String overspeedLimitKmh;
    private String smsAlertNumber;
    private String receiptNumber;

    public String getTripName() {
        return tripName;
    }

    public TripRequest setTripName(String tripName) {
        this.tripName = tripName;
        return this;
    }

    public String getOriginGeofenceSearchTerm() {
        return originGeofenceSearchTerm;
    }

    public TripRequest setOriginGeofenceSearchTerm(String originGeofenceSearchTerm) {
        this.originGeofenceSearchTerm = originGeofenceSearchTerm;
        return this;
    }

    public String getOriginGeofenceMatchText() {
        return originGeofenceMatchText;
    }

    public TripRequest setOriginGeofenceMatchText(String originGeofenceMatchText) {
        this.originGeofenceMatchText = originGeofenceMatchText;
        return this;
    }

    public String getDestinationGeofenceSearchTerm() {
        return destinationGeofenceSearchTerm;
    }

    public TripRequest setDestinationGeofenceSearchTerm(String destinationGeofenceSearchTerm) {
        this.destinationGeofenceSearchTerm = destinationGeofenceSearchTerm;
        return this;
    }

    public String getDestinationGeofenceMatchText() {
        return destinationGeofenceMatchText;
    }

    public TripRequest setDestinationGeofenceMatchText(String destinationGeofenceMatchText) {
        this.destinationGeofenceMatchText = destinationGeofenceMatchText;
        return this;
    }

    public String getDeviceSearchTerm() {
        return deviceSearchTerm;
    }

    public TripRequest setDeviceSearchTerm(String deviceSearchTerm) {
        this.deviceSearchTerm = deviceSearchTerm;
        return this;
    }

    public String getDeviceMatchText() {
        return deviceMatchText;
    }

    public TripRequest setDeviceMatchText(String deviceMatchText) {
        this.deviceMatchText = deviceMatchText;
        return this;
    }

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public TripRequest setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
        return this;
    }

    public String getSecondaryContactNumber() {
        return secondaryContactNumber;
    }

    public TripRequest setSecondaryContactNumber(String secondaryContactNumber) {
        this.secondaryContactNumber = secondaryContactNumber;
        return this;
    }

    public String getOverspeedLimitKmh() {
        return overspeedLimitKmh;
    }

    public TripRequest setOverspeedLimitKmh(String overspeedLimitKmh) {
        this.overspeedLimitKmh = overspeedLimitKmh;
        return this;
    }

    public String getSmsAlertNumber() {
        return smsAlertNumber;
    }

    public TripRequest setSmsAlertNumber(String smsAlertNumber) {
        this.smsAlertNumber = smsAlertNumber;
        return this;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public TripRequest setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
        return this;
    }
}
