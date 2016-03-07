package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class Room implements Serializable{
    private String room;
    private int roomId;
    private int areaId;
    private int ahuId;
    private String roomName;
    private String roomNo;
    private double width;
    private double height;
    private double length;
    private double area;
    private double volume;
    private int acphNLT;
    private String testRef;
    private String isoClause;
    private String occupancyState;
    private double roomSupplyAirflowCFM;
    private double ahuFlowCFM;
    private double roomPressurePA;
    private double freshAirCFM;
    private double bleedAirCFM;
    private double exhaustAirCFM;
    private double temperature;
    private double rh;
    private double returnAirCFM;
    private int supplyAirGrillQTY;
    private int returnAirGrillQTY;
    private int supplyAirFilterQTY;
    private int returnAirFilterQTY;
    private String remarks;
    private String creationDate;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getAhuId() {
        return ahuId;
    }

    public void setAhuId(int ahuId) {
        this.ahuId = ahuId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getAcphNLT() {
        return acphNLT;
    }

    public void setAcphNLT(int acphNLT) {
        this.acphNLT = acphNLT;
    }

    public String getTestRef() {
        return testRef;
    }

    public void setTestRef(String testRef) {
        this.testRef = testRef;
    }

    public String getIsoClause() {
        return isoClause;
    }

    public void setIsoClause(String isoClause) {
        this.isoClause = isoClause;
    }

    public String getOccupancyState() {
        return occupancyState;
    }

    public void setOccupancyState(String occupancyState) {
        this.occupancyState = occupancyState;
    }

    public double getRoomSupplyAirflowCFM() {
        return roomSupplyAirflowCFM;
    }

    public void setRoomSupplyAirflowCFM(double roomSupplyAirflowCFM) {
        this.roomSupplyAirflowCFM = roomSupplyAirflowCFM;
    }

    public double getAhuFlowCFM() {
        return ahuFlowCFM;
    }

    public void setAhuFlowCFM(double ahuFlowCFM) {
        this.ahuFlowCFM = ahuFlowCFM;
    }

    public double getRoomPressurePA() {
        return roomPressurePA;
    }

    public void setRoomPressurePA(double roomPressurePA) {
        this.roomPressurePA = roomPressurePA;
    }

    public double getFreshAirCFM() {
        return freshAirCFM;
    }

    public void setFreshAirCFM(double freshAirCFM) {
        this.freshAirCFM = freshAirCFM;
    }

    public double getBleedAirCFM() {
        return bleedAirCFM;
    }

    public void setBleedAirCFM(double bleedAirCFM) {
        this.bleedAirCFM = bleedAirCFM;
    }

    public double getExhaustAirCFM() {
        return exhaustAirCFM;
    }

    public void setExhaustAirCFM(double exhaustAirCFM) {
        this.exhaustAirCFM = exhaustAirCFM;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getRh() {
        return rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public double getReturnAirCFM() {
        return returnAirCFM;
    }

    public void setReturnAirCFM(double returnAirCFM) {
        this.returnAirCFM = returnAirCFM;
    }

    public int getSupplyAirGrillQTY() {
        return supplyAirGrillQTY;
    }

    public void setSupplyAirGrillQTY(int supplyAirGrillQTY) {
        this.supplyAirGrillQTY = supplyAirGrillQTY;
    }

    public int getReturnAirGrillQTY() {
        return returnAirGrillQTY;
    }

    public void setReturnAirGrillQTY(int returnAirGrillQTY) {
        this.returnAirGrillQTY = returnAirGrillQTY;
    }

    public int getSupplyAirFilterQTY() {
        return supplyAirFilterQTY;
    }

    public void setSupplyAirFilterQTY(int supplyAirFilterQTY) {
        this.supplyAirFilterQTY = supplyAirFilterQTY;
    }

    public int getReturnAirFilterQTY() {
        return returnAirFilterQTY;
    }

    public void setReturnAirFilterQTY(int returnAirFilterQTY) {
        this.returnAirFilterQTY = returnAirFilterQTY;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
