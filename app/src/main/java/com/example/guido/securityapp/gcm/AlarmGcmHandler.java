package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.exceptions.NotRecentAlarm;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;

/**
 * Created by guido on 2/22/15.
 */
public class AlarmGcmHandler extends GcmHandler{

    public AlarmGcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        super(parser, intent, service, command);
        title = MyApplication.getContext().getResources().getString(R.string.alarm_notification_title);
    }

    @Override
    public boolean canHandle() {
        return (intent.getStringExtra("type")!=null && intent.getStringExtra("type").equals(MyApplication.getContext().getResources().getString(R.string.alarm_type)));
    }

    @Override
    protected PendingIntent getIntent() {
        Notification recentAlarm = getRecentNotification();
        String url = recentAlarm.getUrl();

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        return PendingIntent.getActivity(service, 0,
                i, 0);
    }

    protected Notification getRecentNotification()
    {
        try {
            return BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM).getMostRecentNotification();
        } catch (NotRecentAlarm notRecentAlarm) {
           //TODO HANDLE
            return null;
        }
    }
}
