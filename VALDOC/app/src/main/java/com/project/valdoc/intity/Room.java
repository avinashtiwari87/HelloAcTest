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
    private int acph;
    private String testRef;
    private String isoClause;
    private String occupancyState;
    private double supplyAirflow;
    private double ahuFlowCFM;
    private double roomPressure;
    private double freshAirCFM;
    private double bleedAirCFM;
    private double exhaustAirFlow;
    private double temperature;
    private double rh;
    private double returnAirFlow;
//    private int supplyAirGrillQTY;
//    private int returnAirGrillQTY;
//    private int supplyAirFilterQTY;
//    private int returnAirFilterQTY;
    private String remarks;
    private String lastUpdatedDate;

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

    public int getAcph() {
        return acph;
    }

    public void setAcph(int acph) {
        this.acph = acph;
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

    public double getSupplyAirflow() {
        return supplyAirflow;
    }

    public void setSupplyAirflow(double supplyAirflow) {
        this.supplyAirflow = supplyAirflow;
    }

    public double getAhuFlowCFM() {
        return ahuFlowCFM;
    }

    public void setAhuFlowCFM(double ahuFlowCFM) {
        this.ahuFlowCFM = ahuFlowCFM;
    }

    public double getRoomPressure() {
        return roomPressure;
    }

    public void setRoomPressure(double roomPressure) {
        this.roomPressure = roomPressure;
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

    public double getExhaustAirFlow() {
        return exhaustAirFlow;
    }

    public void setExhaustAirFlow(double exhaustAirFlow) {
        this.exhaustAirFlow = exhaustAirFlow;
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

    public double getReturnAirFlow() {
        return returnAirFlow;
    }

    public void setReturnAirFlow(double returnAirFlow) {
        this.returnAirFlow = returnAirFlow;
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
