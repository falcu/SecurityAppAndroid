package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.adapters.BuilderAlarmsHistoryAdapter;
import com.example.guido.securityapp.interfaces.IListFragment;

public class AlarmsHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_history);
        IListFragment listFragment = (IListFragment) getFragmentManager().findFragmentById(R.id.notifications_history_fragment);
        configureList(listFragment);
    }

    protected void configureList(IListFragment listFragment)
    {
        listFragment.setBuilderAdapter(new BuilderAlarmsHistoryAdapter(this));
        listFragment.setEmptyListText("The alarm's history is empty");
    }
}
