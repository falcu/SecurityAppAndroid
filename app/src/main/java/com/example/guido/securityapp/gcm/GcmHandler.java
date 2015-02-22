package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MainActivity;
import com.example.guido.securityapp.commands.Command;

/**
 * Created by guido on 2/22/15.
 */
public abstract class GcmHandler {

    protected GcmParser parser;
    protected Intent intent;
    protected IntentService service;
    protected Command command;

    public GcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        this.parser = parser;
        this.intent = intent;
        this.service = service;
        this.command = command;
    }

    public abstract boolean canHandle();

    public void handle()
    {
        command.execute();
    }

    public NotificationCompat.Builder build()
    {

        PendingIntent contentIntent = getIntent();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(service)
                        .setSmallIcon(getIcon())
                        .setContentTitle(getTitle())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(parser.getMessage()))
                        .setAutoCancel(true)
                        .setContentText(parser.getMessage());


        mBuilder.setContentIntent(contentIntent);

        return mBuilder;
    }

    protected abstract PendingIntent getIntent();


    protected abstract String getTitle();

    protected abstract int getIcon();

}
