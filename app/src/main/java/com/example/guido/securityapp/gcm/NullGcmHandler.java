package com.example.guido.securityapp.gcm;

import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;

import com.example.guido.securityapp.commands.NullCommand;

/**
 * Created by guido on 5/30/15.
 */
public class NullGcmHandler extends GcmHandler{

    public NullGcmHandler() {
        super(null, null, null, new NullCommand());
    }

    @Override
    public boolean canHandle() {
        return true;
    }

    @Override
    public void handle() {
    }

    @Override
    public NotificationCompat.Builder build() {
        return null;
    }

    @Override
    protected PendingIntent getIntent() {
        return null;
    }

    @Override
    protected String getTitle() {
        return null;
    }
}
