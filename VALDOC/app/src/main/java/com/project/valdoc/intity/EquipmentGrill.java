package com.project.valdoc.intity;

import java.io.Serializable;

/**
 * Created by Avinash on 5/28/2016.
 */
public class EquipmentGrill implements Serializable{
    private int grillId;
    private String grillCode;
    private int equipmentId;
    private double length;
    private double width;
    private double area;
    private double effectiveArea;
    private String remarks;
    private String lastUpdatedDate;

    public int getGrillId() {
        return grillId;
    }

    public void setGrillId(int grillId) {
        this.grillId = grillId;
    }

    public String getGrillCode() {
        return grillCode;
    }

    public void setGrillCode(String grillCode) {
        this.grillCode = grillCode;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getEffectiveArea() {
        return effectiveArea;
    }

    public void setEffectiveArea(double effectiveArea) {
        this.effectiveArea = effectiveArea;
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
}
