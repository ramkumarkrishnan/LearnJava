package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class CoCAdditionalDetails {
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
}

