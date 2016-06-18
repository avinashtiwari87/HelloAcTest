package com.project.valdoc.intity;

import java.io.Serializable;

/**
 * Created by Avinash on 5/27/2016.
 */
public class AhuFilter implements Serializable{
    private String ahufilter;
    private int filterId;
    private int ahuId;
    private String filterCategory;
    private String filterCode;
    private double width;
    private double length;
    private double depthArea;
    private double area;
    private double effectiveArea;
    private String lastUpdatedDate;

    public String getAhufilter() {
        return ahufilter;
    }

    public void setAhufilter(String ahufilter) {
        this.ahufilter = ahufilter;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public int getAhuId() {
        return ahuId;
    }

    public void setAhuId(int ahuId) {
        this.ahuId = ahuId;
    }

    public String getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(String filterCategory) {
        this.filterCategory = filterCategory;
    }

    public String getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(String filterCode) {
        this.filterCode = filterCode;
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

    public double getDepthArea() {
        return depthArea;
    }

    public void setDepthArea(double depthArea) {
        this.depthArea = depthArea;
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

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
