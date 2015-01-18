package com.example.guido.securityapp.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.exceptions.MissingDataException;
import com.example.guido.securityapp.interfaces.IDataStore;

/**
 * Created by guido on 1/17/15.
 */
public class ServiceStore implements IDataStore {

    private SharedPreferences sharedPreferences;
    private String identifier;
    private String globalPreferencesKey;
    private Converter converterToLoad;
    private Converter converterToSave;

    public ServiceStore(String identifier,Converter converterToLoad,Converter converterToSave)
    {
        this.identifier = identifier;
        this.converterToLoad = converterToLoad;
        this.converterToSave = converterToSave;
        this.globalPreferencesKey =  MyApplication.getContext().getString(R.string.global_preferences_key);
        this.sharedPreferences = MyApplication.getContext().getApplicationContext().getSharedPreferences(globalPreferencesKey,Context.MODE_PRIVATE);
    }

    @Override
    public Object load() throws Exception {
        String dataString = sharedPreferences.getString(identifier, "");
        if(dataString.isEmpty())
            throw new MissingDataException("There is no data for the identifier "+identifier);

        return converterToLoad.convert(dataString);
    }

    @Override
    public void save(Object data) throws Exception{
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String dataString = (String)converterToSave.convert(data);
        prefsEditor.putString(identifier, dataString);
        prefsEditor.commit();
    }
}
