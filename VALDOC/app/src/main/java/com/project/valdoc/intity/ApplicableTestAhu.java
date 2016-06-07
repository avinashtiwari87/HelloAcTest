package com.project.valdoc.intity;

import java.io.Serializable;

/**
 * Created by Avinash on 5/26/2016.
 */
public class ApplicableTestAhu implements Serializable{

    private String aplicable_test_ahu;
    private int aplicable_testId;
    private int ahuId;
    private String testName;
    private String testCode;
    private String testFormat;
    private String testItem;
    private String testSpecification;
    private String occupencyState;
    private String testReference;
    private String testProp;
    private String periodicity;
    private String location;
    private int noOfCycle;
    private String lastUpdatedDate;

    public String getAplicable_test_ahu() {
        return aplicable_test_ahu;
    }

    public void setAplicable_test_ahu(String aplicable_test_ahu) {
        this.aplicable_test_ahu = aplicable_test_ahu;
    }

    public int getAplicable_testId() {
        return aplicable_testId;
    }

    public void setAplicable_testId(int aplicable_testId) {
        this.aplicable_testId = aplicable_testId;
    }

    public int getAhuId() {
        return ahuId;
    }

    public void setAhuId(int ahuId) {
        this.ahuId = ahuId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestFormat() {
        return testFormat;
    }

    public void setTestFormat(String testFormat) {
        this.testFormat = testFormat;
    }

    public String getTestItem() {
        return testItem;
    }

    public void setTestItem(String testItem) {
        this.testItem = testItem;
    }

    public String getTestSpecification() {
        return testSpecification;
    }

    public void setTestSpecification(String testSpecification) {
        this.testSpecification = testSpecification;
    }

    public String getOccupencyState() {
        return occupencyState;
    }

    public void setOccupencyState(String occupencyState) {
        this.occupencyState = occupencyState;
    }

    public String getTestReference() {
        return testReference;
    }

    public void setTestReference(String testReference) {
        this.testReference = testReference;
    }

    public String getTestProp() {
        return testProp;
    }

    public void setTestProp(String testProp) {
        this.testProp = testProp;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNoOfCycle() {
        return noOfCycle;
    }

    public void setNoOfCycle(int noOfCycle) {
        this.noOfCycle = noOfCycle;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
