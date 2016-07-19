package com.project.valdoc.intity;

/**
 * Created by Avinash on 6/4/2016.
 */
public class IsoParticleLimits {

    private int limitId;
    private String particleClass;
    private String restSmallParticleLimit;
    private String restLargeParticleLimit;
    private String operationSmallParticleLimit;
    private String operationLargeParticleLimit;
    private String lastUpdatedDate;

    public int getLimitId() {
        return limitId;
    }

    public void setLimitId(int limitId) {
        this.limitId = limitId;
    }

    public String getParticleClass() {
        return particleClass;
    }

    public void setParticleClass(String particleClass) {
        this.particleClass = particleClass;
    }

    public String getRestSmallParticleLimit() {
        return restSmallParticleLimit;
    }

    public void setRestSmallParticleLimit(String restSmallParticleLimit) {
        this.restSmallParticleLimit = restSmallParticleLimit;
    }

    public String getRestLargeParticleLimit() {
        return restLargeParticleLimit;
    }

    public void setRestLargeParticleLimit(String restLargeParticleLimit) {
        this.restLargeParticleLimit = restLargeParticleLimit;
    }

    public String getOperationSmallParticleLimit() {
        return operationSmallParticleLimit;
    }

    public void setOperationSmallParticleLimit(String operationSmallParticleLimit) {
        this.operationSmallParticleLimit = operationSmallParticleLimit;
    }

    public String getOperationLargeParticleLimit() {
        return operationLargeParticleLimit;
    }

    public void setOperationLargeParticleLimit(String operationLargeParticleLimit) {
        this.operationLargeParticleLimit = operationLargeParticleLimit;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
