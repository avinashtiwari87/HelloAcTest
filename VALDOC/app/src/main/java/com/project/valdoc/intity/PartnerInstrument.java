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
    private String lastCalibrationDate;
    private String calibrationDueDate;
    private String range;
    private String status;
    private String certFileName;
    private String remarks;
//    private int testId;
    private String lastUpdatedDate;
//    private String  SamplingFlowRate;
//    private String  SamplingTime;
//    private String  AerosolUsed;
//    private String  AerosolGeneratorType;


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

    public String getLastCalibrationDate() {
        return lastCalibrationDate;
    }

    public void setLastCalibrationDate(String lastCalibrationDate) {
        this.lastCalibrationDate = lastCalibrationDate;
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

    public String getCertFileName() {
        return certFileName;
    }

    public void setCertFileName(String certFileName) {
        this.certFileName = certFileName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
