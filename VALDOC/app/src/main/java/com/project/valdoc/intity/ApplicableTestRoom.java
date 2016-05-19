package com.project.valdoc.intity;

import java.util.Date;

/**
 * Created by Avinash on 2/13/2016.
 */
public class ApplicableTestRoom {
    private String aplicable_test_room;
    private int aplicable_testId;
    private int roomId;
    private String testName;
    private String testCode;
    private String testFormat;
    private String testSpecification;
    private String occupencyState;
    private String testReference;
    private String testProp;
    private String periodicity;
    private int location;
    private int noOfCycle;
    private String lastUpdatedDate;

    public String getAplicable_test_room() {
        return aplicable_test_room;
    }

    public void setAplicable_test_room(String aplicable_test_room) {
        this.aplicable_test_room = aplicable_test_room;
    }

    public int getAplicable_testId() {
        return aplicable_testId;
    }

    public void setAplicable_testId(int aplicable_testId) {
        this.aplicable_testId = aplicable_testId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getNoOfCycle() {
        return noOfCycle;
    }

    public void setNoOfCycle(int noOfCycle) {
        this.noOfCycle = noOfCycle;
    }

    public String getTestFormat() {
        return testFormat;
    }

    public void setTestFormat(String testFormat) {
        this.testFormat = testFormat;
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

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
