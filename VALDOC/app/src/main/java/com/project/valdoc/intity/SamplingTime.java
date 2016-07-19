package com.project.valdoc.intity;

/**
 * Created by Avinash on 7/20/2016.
 */
public class SamplingTime {
    private  String sampling_time;
    private int samplingTimeId;
    private String cleanroomClass;
    private String LPM283;
    private String LPM50;
    private String LPM75;
    private String LPM100;
    private String lastUpdatedDate;

    public String getSampling_time() {
        return sampling_time;
    }

    public void setSampling_time(String sampling_time) {
        this.sampling_time = sampling_time;
    }

    public int getSamplingTimeId() {
        return samplingTimeId;
    }

    public void setSamplingTimeId(int samplingTimeId) {
        this.samplingTimeId = samplingTimeId;
    }

    public String getCleanroomClass() {
        return cleanroomClass;
    }

    public void setCleanroomClass(String cleanroomClass) {
        this.cleanroomClass = cleanroomClass;
    }

    public String getLPM283() {
        return LPM283;
    }

    public void setLPM283(String LPM283) {
        this.LPM283 = LPM283;
    }

    public String getLPM50() {
        return LPM50;
    }

    public void setLPM50(String LPM50) {
        this.LPM50 = LPM50;
    }

    public String getLPM75() {
        return LPM75;
    }

    public void setLPM75(String LPM75) {
        this.LPM75 = LPM75;
    }

    public String getLPM100() {
        return LPM100;
    }

    public void setLPM100(String LPM100) {
        this.LPM100 = LPM100;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
