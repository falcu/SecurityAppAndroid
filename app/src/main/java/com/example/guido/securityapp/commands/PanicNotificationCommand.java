package com.example.guido.securityapp.commands;

import android.app.Activity;

import com.example.guido.securityapp.asyncTasks.AlarmTask;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.builders.services.BuilderServicePanicMessage;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.NoAvailableLocation;
import com.example.guido.securityapp.helpers.ConfirmDialogHelper;
import com.example.guido.securityapp.helpers.SettigsHelper;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.OnYesClickListener;
import com.example.guido.securityapp.models.MyLocation;
import com.example.guido.securityapp.models.NotificationTO;
import com.example.guido.securityapp.services.ServiceLocation;

/**
* Created by guido on 2/18/15.
*/
public class PanicNotificationCommand extends Command
{

    private Activity activity;
    private final long MAX_OLD_TIME = 300000;//5 min in milliseconds

    public PanicNotificationCommand(Activity activity){this.activity = activity;}

    @Override
    protected void executeImplementation() {
        try
        {
            if(isLocationAvailable())
            {
                NotificationTO panicTO = makePanicTO();
                AlarmTask task = new AlarmTask(panicTO);
                task.addHandler((ITaskHandler)activity);
                task.execute((Void)null);
            }
            else
            {
                ConfirmDialogHelper confirmDialogHelper = new ConfirmDialogHelper();
                confirmDialogHelper.setOnYesClickListener(new OnYesClickListener() {
                    @Override
                    public void onYesClicked() {
                        SettigsHelper settigsHelper = new SettigsHelper();
                        settigsHelper.showLocationSettings(activity);
                    }
                });
                confirmDialogHelper.showYesNoDialog(activity,"Location Service is not active","Do you want to go to the location service settings?");
            }


        }
        catch (Exception e){
            //TODO HANDLE
        }
    }

    protected boolean isLocationAvailable()
    {
        boolean isAvailable;
        ServiceLocation serviceLocation = BuilderServiceLocationSingleton.getServiceLocation();
        try
        {
            MyLocation lastLocation = serviceLocation.getLocation();
            long timeSinceLastLocation = lastLocation.getTimeOld();
            if(timeSinceLastLocation>MAX_OLD_TIME)
                isAvailable = false;
            else
                isAvailable = true;
        }
        catch(NoAvailableLocation e)
        {
            isAvailable = false;
        }
        return isAvailable;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    private NotificationTO makePanicTO() throws Exception{
        NotificationTO panicTO = new NotificationTO();
        String token = BuilderServiceUserToken.build().getToken();
        String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();
        String message = BuilderServicePanicMessage.build().loadMessage();
        MyLocation location = BuilderServiceLocationSingleton.getServiceLocation().getLocation();
        panicTO.setToken(token);
        panicTO.setGroupId(groupId);
        panicTO.setMessage(message);
        panicTO.setLocation(location);

        return panicTO;

    }
}
