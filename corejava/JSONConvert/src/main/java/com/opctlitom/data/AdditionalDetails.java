package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class AdditionalDetails {
    @SerializedName("operatorcontrol_ocid")
    private String operatorcontrolOcid;
    @SerializedName("operatorcontrol_name")
    private String operatorcontrolName;

    public String getOperatorcontrolOcid() {
        return operatorcontrolOcid;
    }

    public void setOperatorcontrolOcid(String operatorcontrolOcid)
    {
        this.operatorcontrolOcid = operatorcontrolOcid;
    }

    public String getOperatorcontrolName() {
        return operatorcontrolName;
    }

    public void setOperatorcontrolName(String operatorcontrolName) {
        this.operatorcontrolName = operatorcontrolName;
    }

    /*
     * Additional details for createrequest
     *
    @SerializedName("reason")
    private String reason;
    @SerializedName("exadatainfrastructure_ocid")
    private String exadatainfrastructureocid;
    @SerializedName("reasonSummary")
    private String reasonSummary;
    @SerializedName("accessRequestId")
    private String accessRequestId;
    @SerializedName("opCtlId")
    private String opCtlId;
    @SerializedName("accessRequest_url")
    private String accessRequest_url;
    @SerializedName("exadatainfrastructure_name")
    private String exadatainfrastructure_name;
    @SerializedName("opCtlName")
    private String opCtlName;

    public String getReason() {
        return reason;
    }
     */
}

