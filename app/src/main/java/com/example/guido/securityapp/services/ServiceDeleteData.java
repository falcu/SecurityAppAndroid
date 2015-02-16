package com.example.guido.securityapp.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IDataRemover;

/**
 * Created by guido on 2/16/15.
 */
public class ServiceDeleteData implements IDataRemover{

    private SharedPreferences sharedPreferences;
    private String identifier;
    private String globalPreferencesKey;

    public ServiceDeleteData(String identifier)
    {
        this.identifier = identifier;
        this.globalPreferencesKey =  MyApplication.getContext().getString(R.string.global_preferences_key);
        this.sharedPreferences = MyApplication.getContext().getApplicationContext().getSharedPreferences(globalPreferencesKey, Context.MODE_PRIVATE);
    }

    @Override
    public void delete() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.remove(identifier);
        prefsEditor.commit();
    }
}
