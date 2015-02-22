package com.example.guido.securityapp.commands;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.services.ServiceAlarm;

/**
 * Created by guido on 2/22/15.
 */
public class AlarmReceivedCommand extends Command{

    private Intent intent;
    private ServiceAlarm serviceAlarm;

    public AlarmReceivedCommand(Intent intent,ServiceAlarm serviceAlarm)
    {
        this.intent = intent;
        this.serviceAlarm = serviceAlarm;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    protected void executeImplementation() {
        serviceAlarm.saveNewAlarm(intent.getStringExtra("alarm_info"));
    }
}
