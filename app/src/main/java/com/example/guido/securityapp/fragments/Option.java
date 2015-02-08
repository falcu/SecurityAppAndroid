package com.example.guido.securityapp.fragments;

/**
 * Created by guido on 2/8/15.
 */
public class Option
{
    private String text;
    private String key;

    public Option(String text,String key)
    {
        this.text = text;
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}