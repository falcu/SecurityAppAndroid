package com.example.guido.securityapp.commands;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.GroupConfigurationCreatorActivity;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.ICommand;

/**
* Created by guido on 2/16/15.
*/
public class QuitCommand implements ICommand
{

    private GroupConfigurationCreatorActivity activity;

    public QuitCommand(GroupConfigurationCreatorActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public void execute() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.IS_ACTIVITY_FINISH),true);
        new ToastHelper().showLongDurationMessage(activity,"You've quit the group!");
        activity.finishActivityWith(i);
    }
}
