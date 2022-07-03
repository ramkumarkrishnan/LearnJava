package com.opctlitom.data;
import com.google.gson.annotations.SerializedName;

public class OpctlEvent {
    @SerializedName("eventType")
    private String eventType;
    @SerializedName("cloudEventsVersion")
    private String cloudEventsVersion;
    @SerializedName("eventTypeVersion")
    private String eventTypeVersion;
    @SerializedName("source")
    private String source;
    @SerializedName("eventTime")
    private String eventTime;
    @SerializedName("contentType")
    private String contentType;
    @SerializedName("data")
    private Data data;
    @SerializedName("eventID")
    private String eventID;
    @SerializedName("extensions")
    private Extensions extensions;

    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getCloudEventsVersion() {
        return cloudEventsVersion;
    }
    public void setCloudEventsVersion(String cloudEventsVersion) {
        this.cloudEventsVersion = cloudEventsVersion;
    }
    public String getEventTypeVersion() {
        return eventTypeVersion;
    }
    public void setEventTypeVersion(String eventTypeVersion) {
        this.eventTypeVersion = eventTypeVersion;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getEventTime() {
        return eventTime;
    }
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    public String getEventID() {
        return eventID;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public Extensions getExtensions() {
        return extensions;
    }
    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }
}