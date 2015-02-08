package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.ICommand;
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;

public class SecurityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        initializeFragments();
    }

    private void initializeFragments()
    {
        IFragmentDelayedButton delayedAction = (IFragmentDelayedButton) getFragmentManager().findFragmentById(R.id.alarm_fragment);
        delayedAction.setText("ALARM");
        delayedAction.setDelayedAction(new DummyCommand());
    }

    private class DummyCommand implements ICommand
    {

        @Override
        public void execute() {

        }
    }
}
