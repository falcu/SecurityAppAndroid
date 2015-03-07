package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.services.BuilderServiceRegisterId;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.services.ServiceLocation;
import com.example.guido.securityapp.services.ServiceLocationNotifierListener;
import com.example.guido.securityapp.services.ServiceRegisterId;

/**
 * The only purpose of this activity is to control the workflow of the other activities, it does not have
 * a related UI. When the app is run for the first time, the sign_in/sign_out view will be shown to the user,
 * once the user has created an account that view won't appear again unless the user deletes the data, or updates/deletes
 * the app
 */


public class MainActivity extends Activity {

    private ServiceRegisterId serviceRegisterId;
    private ActivityCoordinator activityCoordinator;
    private ServiceLocation serviceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Intent intentService = new Intent(this, ServiceLocationNotifierListener.class);
            startService(intentService);
            serviceLocation = BuilderServiceLocationSingleton.getServiceLocation();
            serviceLocation.startService();
            serviceRegisterId = makeRegisterService();
            serviceRegisterId.setRegistrationIdWithErrorDialogAsync(this);
            activityCoordinator = makeActivityCoordinator();
            activityCoordinator.runCorrespondingActivityFromWithRequestCode(this,MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
        }
        catch (Exception e){
            String message = e.getMessage();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceLocation!=null)
            serviceLocation.stopService();
    }

    protected ActivityCoordinator makeActivityCoordinator()
    {
        return new ActivityCoordinator();
    }

    protected ServiceRegisterId makeRegisterService()
    {
        return BuilderServiceRegisterId.buildRegisterIdService();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.FORCE_FINISH_ACTIVITY),false))
                {
                    try {
                        activityCoordinator.runCorrespondingActivityFromWithRequestCode(this,MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
                    }
                    catch (Exception e)
                    {
                        return;
                    }
                }
                else if(data.getBooleanExtra(MyApplication.getContext().getResources().getString(R.string.BACK_BUTTON_PRESSED),false))
                {
                    finish();
                }

            }
        }
    }
}
