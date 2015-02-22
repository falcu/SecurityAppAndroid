package com.example.guido.securityapp.helpers;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by guido on 2/21/15.
 */
public class SettigsHelper {

    public void showLocationSettings(Activity activity)
    {
        activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
    }
}

