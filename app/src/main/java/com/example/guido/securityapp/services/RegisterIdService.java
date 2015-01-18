package com.example.guido.securityapp.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.exceptions.MissingDataException;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by guido on 1/17/15.
 */
public class RegisterIdService {

    static final String TAG = "RegisterIdService";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String regid = "";
    private GoogleCloudMessaging gcm;
    private IDataStore store;
    private final String SENDER_ID = "595014450651";
    private String errorMessage;

    public RegisterIdService(IDataStore store)
    {
        this.store = store;
    }

    public void setRegistrationId() throws Exception
    {
        errorMessage = "";
        if (isGooglePLayServices()) {
            set();
        }
    }
    public void setRegistrationIdWithErrorDialog(Activity activity) throws Exception
    {
        errorMessage = "";
        if (isGooglePLayServices()) {
            set();
        }
        else {
            showErrorDialog(activity);
        }
    }

    public String getRegistrationId()
    {
        return regid;
    }

    public String getError()
    {
        return errorMessage;
    }

    private void set()  throws Exception
    {
        gcm = GoogleCloudMessaging.getInstance(MyApplication.getContext());

        try
        {
            regid = loadRegistrationId();
        }
        catch (MissingDataException e)
        {
            if (regid.isEmpty()) {
                registerInBackground();
            }
        }

    }

    private int checkGooglePlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MyApplication.getContext());
        return resultCode;
    }

    private boolean isGooglePLayServices()
    {
        return checkGooglePlayServices() == ConnectionResult.SUCCESS;
    }

    private void showErrorDialog(Activity activity)
    {
        int resultCode = checkGooglePlayServices();
        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
            Log.i(TAG, "This device is not supported.");
        }
    }

    private String loadRegistrationId() throws Exception
    {
        String data = (String)store.load();
        return data;
    }

    private void saveRegistrationId(String regId) throws Exception
    {
        store.save(regId);
    }

    private void registerInBackground() throws Exception{
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MyApplication.getContext());
                    }
                    regid = gcm.register(SENDER_ID);

                    // Persist the regID - no need to register again.
                    saveRegistrationId(regid);
                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                errorMessage = msg;
            }
        }.execute(null, null, null);
    }
}
