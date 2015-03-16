package com.example.guido.securityapp.models;

import android.text.format.Time;

import java.util.UUID;

/**
 * Created by guido on 2/21/15.
 */
public class Notification {
    private String location;
    private Member sender;
    private String message;
    private String uid;
    private boolean wasSeen;
    private Time receivedTime;

    public Notification(String url, Member sender, String message) {
        this.setUrl(url);
        this.setSender(sender);
        this.setMessage(message);
        wasSeen = false;
    }

    public String getUrl() {
        return location;
    }

    public void setUrl(String url) {
        this.location = url;
    }

    public Member getSender() {
        return sender;
    }

    public void setSender(Member sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void generateUniqueId() {
        this.uid = UUID.randomUUID().toString();
    }

    public boolean getWasSeen() {
        return wasSeen;
    }

    public void setWasSeen(boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public Time getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTimeToNow() {
        receivedTime = new Time();
        receivedTime.setToNow();
    }
}
