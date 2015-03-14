package com.example.guido.securityapp.commands;

import android.content.Intent;

import com.example.guido.securityapp.services.ServiceAlarmStore;

/**
 * Created by guido on 2/22/15.
 */
public class NotificationReceivedCommand extends Command{

    protected Intent intent;
    protected ServiceAlarmStore serviceAlarmStore;
    protected String infoKey;

    public NotificationReceivedCommand(Intent intent, ServiceAlarmStore serviceAlarmStore, String infoKey)
    {
        this.intent = intent;
        this.serviceAlarmStore = serviceAlarmStore;
        this.infoKey = infoKey;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    protected void executeImplementation() {
        serviceAlarmStore.saveNewNotification(intent.getStringExtra(infoKey));
    }
}
