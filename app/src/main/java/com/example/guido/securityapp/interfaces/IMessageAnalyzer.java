package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 1/25/15.
 */
public interface IMessageAnalyzer {

    public void analyze(Object data);
    public boolean didLastDataHaveMessage();
    public String getLastMessage();

}
