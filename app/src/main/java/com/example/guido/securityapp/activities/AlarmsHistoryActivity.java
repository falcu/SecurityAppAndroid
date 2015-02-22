package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.adapters.BuilderAlarmsHistoryAdapter;
import com.example.guido.securityapp.interfaces.IListFragment;

public class AlarmsHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_history);
        IListFragment listFragment = (IListFragment) getFragmentManager().findFragmentById(R.id.alarms_history_fragment);
        listFragment.setBuilderAdapter(new BuilderAlarmsHistoryAdapter(this));
        listFragment.setEmptyListText("The alarm's history is empty");
    }
}
