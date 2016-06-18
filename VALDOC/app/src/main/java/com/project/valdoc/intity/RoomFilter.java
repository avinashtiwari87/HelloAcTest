package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class RoomFilter implements Serializable{

    private String roomfilter;
    private int filterId;
    private String filterCode;
    private int roomId;
    private String filterType;
    private double efficiency;
    private String particleSize;
    private double specification;
    private double width;
    private double length;
//    private double  area;
    private double effectiveFilterArea;
    private int isSupplyFilter;
//    private String filterLocation;
    private String lastUpdatedDate;

    public String getRoomfilter() {
        return roomfilter;
    }

    public void setRoomfilter(String roomfilter) {
        this.roomfilter = roomfilter;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public String getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(String filterCode) {
        this.filterCode = filterCode;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public String getParticleSize() {
        return particleSize;
    }

    public void setParticleSize(String particleSize) {
        this.particleSize = particleSize;
    }

    public double getSpecification() {
        return specification;
    }

    public void setSpecification(double specification) {
        this.specification = specification;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getEffectiveFilterArea() {
        return effectiveFilterArea;
    }

    public void setEffectiveFilterArea(double effectiveFilterArea) {
        this.effectiveFilterArea = effectiveFilterArea;
    }

    public int getIsSupplyFilter() {
        return isSupplyFilter;
    }

    public void setIsSupplyFilter(int isSupplyFilter) {
        this.isSupplyFilter = isSupplyFilter;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
