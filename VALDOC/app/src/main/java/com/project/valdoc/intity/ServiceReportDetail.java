package com.project.valdoc.intity;

/**
 * Created by Avinash on 3/20/2016.
 */
public class ServiceReportDetail {

    public String service_report_detail;
    public int id;
    public int service_report_id;
    public String typeOfTest;
    public String areaOfTest;
    public String equipmentAhuNo;
    public int NoOfLoc;
    public String NoOfHourDays;

    public String getService_report_detail() {
        return service_report_detail;
    }

    public void setService_report_detail(String service_report_detail) {
        this.service_report_detail = service_report_detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_report_id() {
        return service_report_id;
    }

    public void setService_report_id(int service_report_id) {
        this.service_report_id = service_report_id;
    }

    public String getTypeOfTest() {
        return typeOfTest;
    }

    public void setTypeOfTest(String typeOfTest) {
        this.typeOfTest = typeOfTest;
    }

    public String getAreaOfTest() {
        return areaOfTest;
    }

    public void setAreaOfTest(String areaOfTest) {
        this.areaOfTest = areaOfTest;
    }

    public String getEquipmentAhuNo() {
        return equipmentAhuNo;
    }

    public void setEquipmentAhuNo(String equipmentAhuNo) {
        this.equipmentAhuNo = equipmentAhuNo;
    }

    public int getNoOfLoc() {
        return NoOfLoc;
    }

    public void setNoOfLoc(int noOfLoc) {
        NoOfLoc = noOfLoc;
    }

    public String getNoOfHourDays() {
        return NoOfHourDays;
    }

    public void setNoOfHourDays(String noOfHourDays) {
        NoOfHourDays = noOfHourDays;
    }
}
