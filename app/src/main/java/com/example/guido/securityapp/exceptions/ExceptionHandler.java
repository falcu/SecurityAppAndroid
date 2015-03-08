package com.example.guido.securityapp.exceptions;

import android.app.Activity;

/**
 * Created by guido on 3/7/15.
 */
public abstract class ExceptionHandler {

    protected Activity activity;

    public ExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    public void handleException(Exception e)
    {
        if(e.getClass().equals(HttpTimeOutException.class))
        {
            showError();
        }
    }

    protected abstract void showError();
}
