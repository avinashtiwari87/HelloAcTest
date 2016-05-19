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
    private String occupancyState;
    private double minVelocity;
    private double maxVelocity;
    private String testReference;
    private int filterQuantity;
    private double equipmentLoad;
    private String isoClause;
    private String creationDate;

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

    public String getOccupancyState() {
        return occupancyState;
    }

    public void setOccupancyState(String occupancyState) {
        this.occupancyState = occupancyState;
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

    public String getTestReference() {
        return testReference;
    }

    public void setTestReference(String testReference) {
        this.testReference = testReference;
    }

    public int getFilterQuantity() {
        return filterQuantity;
    }

    public void setFilterQuantity(int filterQuantity) {
        this.filterQuantity = filterQuantity;
    }

    public double getEquipmentLoad() {
        return equipmentLoad;
    }

    public void setEquipmentLoad(double equipmentLoad) {
        this.equipmentLoad = equipmentLoad;
    }

    public String getIsoClause() {
        return isoClause;
    }

    public void setIsoClause(String isoClause) {
        this.isoClause = isoClause;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
