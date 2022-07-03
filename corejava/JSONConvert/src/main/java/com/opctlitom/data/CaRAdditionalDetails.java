package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class CaRAdditionalDetails {

    @SerializedName("reason")
    private String reason;
    @SerializedName("exadatainfrastructure_ocid")
    private String exadatainfrastructureOcid;
    @SerializedName("reasonSummary")
    private String reasonSummary;
    @SerializedName("accessRequestId")
    private String accessRequestId;
    @SerializedName("opCtlId")
    private String opCtlId;
    @SerializedName("accessRequest_url")
    private String accessRequestUrl;
    @SerializedName("exadatainfrastructure_name")
    private String exadatainfrastructureName;
    @SerializedName("opCtlName")

    private String opCtlName;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExadatainfrastructureOcid() {
        return exadatainfrastructureOcid;
    }

    public void setExadatainfrastructureOcid(String exadatainfrastructureOcid) {
        this.exadatainfrastructureOcid = exadatainfrastructureOcid;
    }

    public String getReasonSummary() {
        return reasonSummary;
    }

    public void setReasonSummary(String reasonSummary) {
        this.reasonSummary = reasonSummary;
    }

    public String getAccessRequestId() {
        return accessRequestId;
    }

    public void setAccessRequestId(String accessRequestId) {
        this.accessRequestId = accessRequestId;
    }

    public String getOpCtlId() {
        return opCtlId;
    }

    public void setOpCtlId(String opCtlId) {
        this.opCtlId = opCtlId;
    }

    public String getAccessRequestUrl() {
        return accessRequestUrl;
    }

    public void setAccessRequestUrl(String accessRequestUrl) {
        this.accessRequestUrl = accessRequestUrl;
    }

    public String getExadatainfrastructureName() {
        return exadatainfrastructureName;
    }

    public void setExadatainfrastructureName(String exadatainfrastructureName) {
        this.exadatainfrastructureName = exadatainfrastructureName;
    }

    public String getOpCtlName() {
        return opCtlName;
    }

    public void setOpCtlName(String opCtlName) {
        this.opCtlName = opCtlName;
    }
}
