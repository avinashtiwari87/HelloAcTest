package com.project.valdoc.intity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class EquipmentFilter implements Serializable {
    private String equipmentfilter;
    private int filterId;
    private String filterCode;
    private int equipmentId;

    private String filterType;
    private double efficiency;
    private double particleSize;
    private double specificationLeak;

    private double width;
    private double length;
    private double grillArea;

    private String lastUpdatedDate;

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

    public double getParticleSize() {
        return particleSize;
    }

    public void setParticleSize(double particleSize) {
        this.particleSize = particleSize;
    }

    public double getSpecificationLeak() {
        return specificationLeak;
    }

    public void setSpecificationLeak(double specificationLeak) {
        this.specificationLeak = specificationLeak;
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

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
