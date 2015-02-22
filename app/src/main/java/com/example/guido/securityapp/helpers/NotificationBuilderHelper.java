package com.example.guido.securityapp.helpers;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MainActivity;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.gcm.GcmParser;
import com.example.guido.securityapp.models.Group;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guido on 2/21/15.
 */
public class NotificationBuilderHelper {

    protected GcmParser parser;
    protected IntentService service;

    public NotificationBuilderHelper(GcmParser parser,IntentService service) {
        this.parser = parser;
        this.service = service;
    }

    public NotificationCompat.Builder build()
    {

        PendingIntent contentIntent = PendingIntent.getActivity(service, 0,
                new Intent(service, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(service)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getTitle())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(parser.getMessage()))
                        .setAutoCancel(true)
                        .setContentText(parser.getMessage());


        mBuilder.setContentIntent(contentIntent);

        return mBuilder;
    }

    private String getTitle()
    {
        switch (parser.getResponseTypeObject())
        {
            case GROUP:
                return "Group notification";

        }
        return "";
    }
}
