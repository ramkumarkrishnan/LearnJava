package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class Extensions {
    @SerializedName("compartmentId")
    private String compartmentId;

    public String getCompartmentId() {
        return compartmentId;
    }
    public void setCompartmentId(String compartmentId) {
        this.compartmentId = compartmentId;
    }
}