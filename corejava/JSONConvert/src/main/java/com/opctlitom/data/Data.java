package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("compartmentId")
    private String compartmentId;
    @SerializedName("compartmentName")
    private String compartmentName;
    @SerializedName("resourceName")
    private String resourceName;
    @SerializedName("resourceId")
    private String resourceId;
    @SerializedName("availabilityDomain")
    private String availabilityDomain;
    @SerializedName("additionalDetails")
    private AdditionalDetails additionalDetails;

    public String getCompartmentId() {
        return compartmentId;
    }
    public void setCompartmentId(String compartmentId) {
        this.compartmentId = compartmentId;
    }
    public String getCompartmentName() {
        return compartmentName;
    }
    public void setCompartmentName(String compartmentName) {
        this.compartmentName = compartmentName;
    }
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    public String getAvailabilityDomain() {
        return availabilityDomain;
    }
    public void setAvailabilityDomain(String availabilityDomain) {
        this.availabilityDomain = availabilityDomain;
    }
    public AdditionalDetails getAdditionalDetails() {
        return additionalDetails;
    }
    public void setAdditionalDetails(AdditionalDetails additionalDetails) {
        this.additionalDetails = additionalDetails;
    }
}
