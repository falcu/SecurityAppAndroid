package com.example.guido.securityapp.commands;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guido on 2/19/15.
 */
public class GroupChangedGcmCommand extends Command {

    protected Intent intent;
    protected IEventAggregator eventAggregator;
    protected IDataStore store;

    public GroupChangedGcmCommand(Intent intent,IEventAggregator eventAggregator,IDataStore store)
    {
        this.intent = intent;
        this.eventAggregator = eventAggregator;
        this.store = store;
    }


    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    protected void executeImplementation() {
        try {
            store.save(intent.getStringExtra("group_info"));
            Group group = (Group) store.load();
            eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.group_store_key),group);
        } catch (Exception e) {
            //TODO HANDLE EXCEPTION
        }
    }
}
