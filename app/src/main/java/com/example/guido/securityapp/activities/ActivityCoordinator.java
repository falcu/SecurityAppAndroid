package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;

import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.services.ServiceGroupInformation;
import com.example.guido.securityapp.services.ServiceSign;

import java.util.Dictionary;

/**
 * Created by guido on 2/7/15.
 */
public class ActivityCoordinator {
    private ServiceSign serviceSign;
    private ServiceGroupInformation serviceGroup;

    public ActivityCoordinator()
    {
        serviceSign = BuilderSignService.buildServiceSign(BuilderSignService.SignOptions.SIGN_IN);
        serviceGroup = BuilderGroupService.buildGroupInformationService();
    }

    public void runCorrespondingActivityFromWithRequestCode(Activity mainActivity,int requestCode) throws Exception
    {
        if(mainActivity==null)
            throw new IllegalArgumentException("Main activity cannot be null");

        Intent intent = makeIntent(mainActivity,SecurityActivity.class);


        if(!serviceGroup.belongsToGroup())
        {
            intent = makeIntent(mainActivity,CreateGroupActivity.class);
        }
        if(!serviceSign.isUserSingedIn())
        {
            intent = makeIntent(mainActivity,SignInActivity.class);
        }

        if(intent!=null)
              mainActivity.startActivityForResult(intent,requestCode);

    }

    private Intent makeIntent(Activity mainActivity,Class activityTuRun) throws Exception
    {
        Intent i = new Intent(mainActivity,activityTuRun);
        if(!mainActivity.getClass().equals(MainActivity.class))
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return i;
    }
}
