package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MainActivity;
import com.example.guido.securityapp.builders.commands.BuilderGcmCommands;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.helpers.NotificationBuilderHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/18/15.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    private BuilderGcmCommands commandBuilder;
    private GcmParser parser;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        commandBuilder = new BuilderGcmCommands();
       // parser = new GcmParser(intent);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                 //TODO PROCESS MESSAGE

                Iterator<Command> commands = commandBuilder.getCommands(intent);
                while(commands.hasNext())
                {
                    commands.next().execute();
                }

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(intent);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Intent intent) {

        GcmParser parser = new GcmParser(intent);
        NotificationBuilderHelper builderHelper = new NotificationBuilderHelper(parser,this);

        notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = builderHelper.build();
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}