package com.example.guido.securityapp.models;

/**
 * Created by guido on 2/21/15.
 */
public class Alarm {
    private String url;
    private Member sender;
    private String message;

    public Alarm(String url, Member sender, String message) {
        this.setUrl(url);
        this.setSender(sender);
        this.setMessage(message);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
