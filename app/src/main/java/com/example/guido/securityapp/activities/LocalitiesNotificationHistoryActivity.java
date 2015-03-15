package com.example.guido.securityapp.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.adapters.BuilderAlarmsHistoryAdapter;
import com.example.guido.securityapp.builders.adapters.BuilderLocalitiesNotificationAdapter;
import com.example.guido.securityapp.interfaces.IListFragment;

public class LocalitiesNotificationHistoryActivity extends AlarmsHistoryActivity {

    @Override
    protected void configureList(IListFragment listFragment) {
        listFragment.setBuilderAdapter(new BuilderLocalitiesNotificationAdapter(this));
        listFragment.setEmptyListText("The notifications' history is empty");
    }
}
