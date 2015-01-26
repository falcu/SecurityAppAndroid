package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.fragments.DescriptionFragment;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;

public class CreateGroupActivity extends Activity implements IFragmentResultHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        initialize();
    }

    private void initialize()
    {
        hideFragment(R.id.create_group_fragment);
        hideFragment(R.id.description_fragment);
        ((DescriptionFragment)getFragmentManager().findFragmentById(R.id.description_fragment)).setDescription("In order to join a group, the creator must add you to the group. The creator just needs your email");
    }


    @Override
    public void handle(Object data) {
        String key = (String)data;
        if(key.equals(getString(R.string.create_group_key)))
        {
            showFragment(R.id.create_group_fragment);
            hideFragment(R.id.description_fragment);
        }
        else if(key.equals(getString(R.string.join_group_key)))
        {
            hideFragment(R.id.create_group_fragment);
            showFragment(R.id.description_fragment);
        }

    }

    private void showFragment(int id)
    {
        ((IFragmentVisibility) getFragmentManager().findFragmentById(id)).show();
    }

    private void hideFragment(int id)
    {
        ((IFragmentVisibility) getFragmentManager().findFragmentById(id)).hide();
    }
}
