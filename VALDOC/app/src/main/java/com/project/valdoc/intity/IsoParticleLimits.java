package com.project.valdoc.intity;

/**
 * Created by Avinash on 6/4/2016.
 */
public class IsoParticleLimits {

    private int limitId;
    private String isoClass;
    private String restSmallParticleLimit;
    private String restLargeParticleLimit;
    private String operationSmallParticleLimit;
    private String operationLargeParticleLimit;

    public int getLimitId() {
        return limitId;
    }

    public void setLimitId(int limitId) {
        this.limitId = limitId;
    }

    public String getIsoClass() {
        return isoClass;
    }

    public void setIsoClass(String isoClass) {
        this.isoClass = isoClass;
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
}
