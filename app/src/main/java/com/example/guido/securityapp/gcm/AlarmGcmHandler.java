package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MainActivity;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarm;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.models.Alarm;
import com.example.guido.securityapp.models.AlarmsHistory;

/**
 * Created by guido on 2/22/15.
 */
public class AlarmGcmHandler extends GcmHandler{

    public AlarmGcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        super(parser, intent, service, command);
    }

    @Override
    public boolean canHandle() {
        return (intent.getStringExtra("type").equals(MyApplication.getContext().getResources().getString(R.string.alarm_type)));
    }

    @Override
    protected PendingIntent getIntent() {
         AlarmsHistory alarmsHistory = BuilderServiceAlarm.build().getAlarmsHistory();
        Alarm recentAlarm = alarmsHistory.getAlarms().get(0);
        String url = recentAlarm.getUrl();

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        return PendingIntent.getActivity(service, 0,
                i, 0);
    }

    @Override
    protected String getTitle() {
        return "ALARM";
    }

    @Override
    protected int getIcon() {
        return R.drawable.ic_launcher;
    }
}
