package com.sai.customerapp.FragmentClasses;

public class TenantInfo {

    public int tenantID;
    public String tenantProfPic;
    public String tenantName;
    public String tenantDescription;

    public TenantInfo() {

    }

    public TenantInfo(int tenantID, String tenantProfPic, String tenantName, String tenantDescription) {
        this.tenantID = tenantID;
        this.tenantProfPic = tenantProfPic;
        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;
    }

}