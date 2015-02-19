package com.example.guido.securityapp.commands;

import com.example.guido.securityapp.asyncTasks.PanicNotificationTask;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.interfaces.ICommand;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.MyLocation;
import com.example.guido.securityapp.models.PanicTO;

/**
* Created by guido on 2/18/15.
*/
public class PanicNotificationCommand implements ICommand
{

    private String message = "I need help";
    private ITaskHandler handler;

    public PanicNotificationCommand(ITaskHandler handler){this.handler = handler;}
    public PanicNotificationCommand(ITaskHandler handler, String message){
        this(handler);
        this.message = message;}

    @Override
    public void execute() {
        try
        {
            PanicTO panicTO = makePanicTO();
            PanicNotificationTask task = new PanicNotificationTask(panicTO);
            task.addHandler(handler);
            task.execute((Void)null);

        }
        catch (Exception e){
            //TODO HANDLE
        }
    }

    private PanicTO makePanicTO() throws Exception{
        PanicTO panicTO = new PanicTO();
        String token = BuilderServiceUserToken.build().getToken();
        String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();
        MyLocation location = BuilderServiceLocationSingleton.getServiceLocation().getLocation();
        panicTO.setToken(token);
        panicTO.setGroupId(groupId);
        panicTO.setMessage(message);
        panicTO.setLocation(location);

        return panicTO;

    }
}
