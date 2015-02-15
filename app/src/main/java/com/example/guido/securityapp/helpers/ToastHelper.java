package com.example.guido.securityapp.helpers;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by guido on 2/11/15.
 */
public class ToastHelper {

    public ToastHelper(){}

    public void showLongDurationMessage(Context context,String message)
    {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }
}
