package com.project.valdoc.intity;

import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class EquipmentFilter {
    private String equipmentfilter;
    private int filterId;
    private String filterCode;
    private int equipmentId;
    private double width;
    private double length;
    private double grillArea;
    private double effectiveGrillArea;
    private int isSupplyFilter;
    private String creationDate;

    public String getEquipmentfilter() {
        return equipmentfilter;
    }

    public void setEquipmentfilter(String equipmentfilter) {
        this.equipmentfilter = equipmentfilter;
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

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
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

    public double getGrillArea() {
        return grillArea;
    }

    public void setGrillArea(double grillArea) {
        this.grillArea = grillArea;
    }

    public double getEffectiveGrillArea() {
        return effectiveGrillArea;
    }

    public void setEffectiveGrillArea(double effectiveGrillArea) {
        this.effectiveGrillArea = effectiveGrillArea;
    }

    public int getIsSupplyFilter() {
        return isSupplyFilter;
    }

    public void setIsSupplyFilter(int isSupplyFilter) {
        this.isSupplyFilter = isSupplyFilter;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
