package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IDataStore;

/**
 * Created by guido on 2/22/15.
 */
public class ServicePanicMessage {

    protected IDataStore store;
    protected String defaultMessage;

    public ServicePanicMessage(IDataStore store) {
        this.store = store;
        defaultMessage = MyApplication.getContext().getResources().getString(R.string.default_panic_message);
    }

    public void saveMessage(String message)
    {
        try {
            store.save(message);
        } catch (Exception e) {
            //TODO HANDLE EXCEPTION
            e.printStackTrace();
        }
    }

    public String loadMessage()
    {
        try {
            return (String) store.load();
        } catch (Exception e) {
            return defaultMessage;
        }
    }


}
