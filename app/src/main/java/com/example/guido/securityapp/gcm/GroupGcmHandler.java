package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MainActivity;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.activities.SecurityActivity;
import com.example.guido.securityapp.commands.Command;

import java.util.Arrays;

/**
 * Created by guido on 2/22/15.
 */
public class GroupGcmHandler extends GcmHandler {


    public GroupGcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        super(parser, intent, service, command);
    }

    @Override
    public boolean canHandle() {
        boolean can = false;
        if(intent.getStringExtra("type")!=null)
        {
            String[] types = MyApplication.getContext().getResources().getStringArray(R.array.group_gcm_types);
            if(Arrays.asList(types).contains(intent.getStringExtra("type")))
            {
                can = true;
            }
        }
        return can;
    }

    @Override
    protected PendingIntent getIntent() {
        Intent i = new Intent(service, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(service, 0,i, 0);
    }

    @Override
    protected String getTitle() {
        return "Group notification";
    }

    @Override
    protected int getIcon() {
        return R.drawable.ic_launcher;
    }
}
