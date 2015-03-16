package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.exceptions.NotRecentAlarm;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;

/**
 * Created by guido on 3/12/15.
 */
public class LocalityNotificationGcmHandler extends AlarmGcmHandler {
    public LocalityNotificationGcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        super(parser, intent, service, command);
    }

    @Override
    public boolean canHandle() {
        return (intent.getStringExtra("type").equals(MyApplication.getContext().getResources().getString(R.string.locality_notification_type)));
    }

    @Override
    protected Notification getRecentNotification()
    {
        try {
            return BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.LOCALITY_NOTIFICATION).getMostRecentNotification();
        } catch (NotRecentAlarm notRecentAlarm) {
            //TODO HANDLE
            return null;
        }
    }

    @Override
    protected String getTitle() {
        return "LOCATION NOTIFICATION";
    }
}
