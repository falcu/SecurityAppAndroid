package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;

import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.GetGroupInfoTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceSign;
import com.example.guido.securityapp.exceptions.ExceptionHandler;
import com.example.guido.securityapp.exceptions.ExceptionHandlerWithDialog;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.services.ServiceGroupInformation;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * Created by guido on 2/7/15.
 */
public class ActivityCoordinator implements ITaskHandler{
    private ServiceSign serviceSign;
    private ServiceGroupInformation serviceGroup;
    private AsynTaskWithHandlers groupInfoTask;

    private MainActivity mainActivity;
    private int requestCode;

    public ActivityCoordinator()
    {
        serviceSign = BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_IN);
        serviceGroup = BuilderServiceGroup.buildGroupInformationService();
    }

    public void runCorrespondingActivityFromWithRequestCode(MainActivity mainActivity,int requestCode) throws Exception
    {
        if(mainActivity==null)
            throw new IllegalArgumentException("Main activity cannot be null");

        this.mainActivity = mainActivity;

        if(groupInfoTask!=null)
        {
            groupInfoTask.cancel(true);
            groupInfoTask = null;
        }
        Intent intent = null;

        if(!serviceSign.isUserSingedIn())
        {
            intent = makeIntent(mainActivity,SignInActivity.class);
        }

        else if(!serviceGroup.belongsToGroup())
        {
           this.requestCode = requestCode;
           groupInfoTask = new GetGroupInfoTask();
           groupInfoTask.addHandler(this);
           groupInfoTask.execute((Void)null);
        }

        else
        {
            intent = makeIntent(mainActivity,SecurityActivity.class);
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

    @Override
    public void onPreExecute(String identifier) {
        mainActivity.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        Intent intent;
        mainActivity.showProgress(false);
        try {
            if (taskResult.isSuccesful()) {
                intent = makeIntent(mainActivity, SecurityActivity.class);
            } else {
                intent = makeIntent(mainActivity, CreateGroupActivity.class);
            }
            mainActivity.startActivityForResult(intent,requestCode);
        } catch (Exception e) {
            return;
        }

    }

    @Override
    public void onCancelled(String identifier) {
        mainActivity.showProgress(false);
    }
}
