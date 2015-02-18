package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.services.BuilderServiceRegisterId;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.services.ServiceLocation;
import com.example.guido.securityapp.services.ServiceRegisterId;


public class MainActivity extends Activity {

    private ServiceRegisterId serviceRegisterId;
    private ISignService signService;
    private ActivityCoordinator activityCoordinator;
    private ServiceLocation serviceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        //TODO REMOVE DUMMY CODE

        try
        {
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
    protected void onDestroy() {
        super.onDestroy();
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

            }
        }
    }
}
