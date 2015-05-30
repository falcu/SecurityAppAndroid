package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.commands.Command;

/**
 * Created by guido on 3/15/15.
 */
public class DeletedFromGroupGcmHandler extends GroupGcmHandler{
    public DeletedFromGroupGcmHandler(GcmParser parser, Intent intent, IntentService service, Command command) {
        super(parser, intent, service, command);
    }

    @Override
    public boolean canHandle() {
        return intent.getStringExtra("type")!=null && intent.getStringExtra("type").equals(MyApplication.getContext().getResources().getString(R.string.deleted_from_group_type));
    }
}
