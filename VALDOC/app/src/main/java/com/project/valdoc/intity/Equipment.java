package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class Equipment implements Serializable{
    private String equipment;
    private int equipmentId;
    private String equipmentNo;
    private String equipmentName;
    private int roomId;
    private double minVelocity;
    private double maxVelocity;
    private int supplyAirflow;
    private int equipmentPressure;
    private int exhustAirflow;
    private String remarks;
    private String lastUpdatedDate;


    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getMinVelocity() {
        return minVelocity;
    }

    public void setMinVelocity(double minVelocity) {
        this.minVelocity = minVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getSupplyAirflow() {
        return supplyAirflow;
    }

    public void setSupplyAirflow(int supplyAirflow) {
        this.supplyAirflow = supplyAirflow;
    }

    public int getEquipmentPressure() {
        return equipmentPressure;
    }

    public void setEquipmentPressure(int equipmentPressure) {
        this.equipmentPressure = equipmentPressure;
    }

    public int getExhustAirflow() {
        return exhustAirflow;
    }

    public void setExhustAirflow(int exhustAirflow) {
        this.exhustAirflow = exhustAirflow;
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
