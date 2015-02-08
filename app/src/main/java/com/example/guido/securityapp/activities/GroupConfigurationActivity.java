package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.fragments.OptionsFragment;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;

public class GroupConfigurationActivity extends Activity implements IFragmentResultHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_configuration);
        initialize();
    }

    private void initialize()
    {
        IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
        fragmentOptions.addOption(new Option("my text1","my key1"));
        fragmentOptions.addOption(new Option("my text2","my key2"));
    }

    @Override
    public void handle(Object data) {

    }
}
