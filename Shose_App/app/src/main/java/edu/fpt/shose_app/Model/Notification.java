package edu.fpt.shose_app.Model;

public class Notification {
    private String timestap;
    private FCMRequest fcmRequest;

    public Notification() {
    }

    public Notification(String timestap, FCMRequest fcmRequest) {
        this.timestap = timestap;
        this.fcmRequest = fcmRequest;
    }

    public String getTimestap() {
        return timestap;
    }

    public void setTimestap(String timestap) {
        this.timestap = timestap;
    }

    public FCMRequest getFcmRequest() {
        return fcmRequest;
    }

    public void setFcmRequest(FCMRequest fcmRequest) {
        this.fcmRequest = fcmRequest;
    }
}
