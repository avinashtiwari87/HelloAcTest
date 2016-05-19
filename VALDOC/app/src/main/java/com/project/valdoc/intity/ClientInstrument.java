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
//    private String currentLocation;
    private String status;
//    private int testId;
    private String lastUpdatedDate;
//    private String  SamplingFlowRate;
//    private String  SamplingTime;
//    private String  AerosolUsed;
//    private String  AerosolGeneratorType;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
