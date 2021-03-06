package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class Grill implements Serializable{
    private String grill;
    private int grillId;
    private String grillCode;
    private int roomId;
    private double length;
    private double widh;
    private double grillArea;
    private double effectiveArea;
    private int isSupplyGrill;
    private int additionalDetail;
    private String lastUpdatedDate;

    public String getGrill() {
        return grill;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidh() {
        return widh;
    }

    public void setWidh(double widh) {
        this.widh = widh;
    }

    public double getGrillArea() {
        return grillArea;
    }

    public void setGrillArea(double grillArea) {
        this.grillArea = grillArea;
    }

    public double getEffectiveArea() {
        return effectiveArea;
    }

    public void setEffectiveArea(double effectiveArea) {
        this.effectiveArea = effectiveArea;
    }

    public int getIsSupplyGrill() {
        return isSupplyGrill;
    }

    public void setIsSupplyGrill(int isSupplyGrill) {
        this.isSupplyGrill = isSupplyGrill;
    }

    public int getAdditionalDetail() {
        return additionalDetail;
    }

    public void setAdditionalDetail(int additionalDetail) {
        this.additionalDetail = additionalDetail;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
