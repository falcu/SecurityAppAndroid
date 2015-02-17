package com.example.guido.securityapp.commands;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.GroupConfigurationCreatorActivity;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.ICommand;

/**
* Created by guido on 2/16/15.
*/
public class FinishCommand implements ICommand
{

    private GroupConfigurationCreatorActivity activity;

    public FinishCommand(GroupConfigurationCreatorActivity activity)
    {
        this.activity = activity;
    }
    @Override
    public void execute() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.UPDATE_GROUP),true);
        this.activity.finishActivityWith(i);
    }
}
