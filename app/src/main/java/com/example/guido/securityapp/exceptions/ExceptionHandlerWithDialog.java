package com.example.guido.securityapp.exceptions;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created by guido on 3/7/15.
 */
public class ExceptionHandlerWithDialog extends ExceptionHandler {
    public ExceptionHandlerWithDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void showError() {
        new AlertDialog.Builder(activity).setTitle("Error").setMessage("Unable to perform operation, try again later").show();
    }
}
