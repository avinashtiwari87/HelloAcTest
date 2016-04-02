package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class PartnerInstrument implements Serializable{
    private String partner_instrument;
    private int pInstrumentId;
    private int partnerId;
    private String serialNo;
    private String pInstrumentName;
    private String make;
    private String model;
    private String lastCalibrated;
    private String calibrationDueDate;
    private String currentLocation;
    private String status;
    private int testId;
    private String creationDate;

    public String getPartner_instrument() {
        return partner_instrument;
    }

    public void setPartner_instrument(String partner_instrument) {
        this.partner_instrument = partner_instrument;
    }

    public int getpInstrumentId() {
        return pInstrumentId;
    }

    public void setpInstrumentId(int pInstrumentId) {
        this.pInstrumentId = pInstrumentId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getpInstrumentName() {
        return pInstrumentName;
    }

    public void setpInstrumentName(String pInstrumentName) {
        this.pInstrumentName = pInstrumentName;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLastCalibrated() {
        return lastCalibrated;
    }

    public void setLastCalibrated(String lastCalibrated) {
        this.lastCalibrated = lastCalibrated;
    }

    public String getCalibrationDueDate() {
        return calibrationDueDate;
    }

    public void setCalibrationDueDate(String calibrationDueDate) {
        this.calibrationDueDate = calibrationDueDate;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
