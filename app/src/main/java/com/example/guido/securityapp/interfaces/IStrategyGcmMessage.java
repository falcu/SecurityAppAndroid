package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 2/19/15.
 */
public interface IStrategyGcmMessage {

    public boolean canHandle(String gcmMessageType);
}
