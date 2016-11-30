package com.project.valdoc.intity;

/**
 * Created by Avinash on 2/20/2016.
 */
public class PartnerUser {
    private String partneruser;
    private int partner_user_id;
    private int app_user_id;
    private int partnerId;
    private String creationDate;

    public String getPartneruser() {
        return partneruser;
    }

    public void setPartneruser(String partneruser) {
        this.partneruser = partneruser;
    }

    public int getPartner_user_id() {
        return partner_user_id;
    }

    public void setPartner_user_id(int partner_user_id) {
        this.partner_user_id = partner_user_id;
    }

    public int getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(int app_user_id) {
        this.app_user_id = app_user_id;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
