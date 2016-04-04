package com.project.valdoc.intity;

import java.io.Serializable;

/**
 * Created by Avinash on 2/20/2016.
 */
public class ClientInstrument implements Serializable{
    private  String client_instrument;
    private int cInstrumentId;
    private String instrumentId;
    private String serialNo;
    private String cInstrumentName;
    private String make;
    private String model;
    private String lastCalibrated;
    private String calibrationDueDate;
    private String currentLocation;
    private String status;
    private int testId;
    private String creationDate;
    private String  SamplingFlowRate;
    private String  SamplingTime;
    private String  AerosolUsed;
    private String  AerosolGeneratorType;

    public String getClient_instrument() {
        return client_instrument;
    }

    public void setClient_instrument(String client_instrument) {
        this.client_instrument = client_instrument;
    }

    public int getcInstrumentId() {
        return cInstrumentId;
    }

    public void setcInstrumentId(int cInstrumentId) {
        this.cInstrumentId = cInstrumentId;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getcInstrumentName() {
        return cInstrumentName;
    }

    public void setcInstrumentName(String cInstrumentName) {
        this.cInstrumentName = cInstrumentName;
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

    public String getSamplingFlowRate() {
        return SamplingFlowRate;
    }

    public void setSamplingFlowRate(String samplingFlowRate) {
        SamplingFlowRate = samplingFlowRate;
    }

    public String getSamplingTime() {
        return SamplingTime;
    }

    public void setSamplingTime(String samplingTime) {
        SamplingTime = samplingTime;
    }

    public String getAerosolUsed() {
        return AerosolUsed;
    }

    public void setAerosolUsed(String aerosolUsed) {
        AerosolUsed = aerosolUsed;
    }

    public String getAerosolGeneratorType() {
        return AerosolGeneratorType;
    }

    public void setAerosolGeneratorType(String aerosolGeneratorType) {
        AerosolGeneratorType = aerosolGeneratorType;
    }
}
