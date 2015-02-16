package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceNotification;
import com.example.guido.securityapp.models.PanicTO;
import com.example.guido.securityapp.services.ServiceNotification;

/**
 * Created by guido on 2/14/15.
 */
public class PanicNotificationTask extends AsynTaskWithHandlers{
    protected PanicTO panicTO;

    public PanicNotificationTask(PanicTO panicTO) throws Exception
    {
        super(panicTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        ServiceNotification service = BuilderServiceNotification.build();
        try
        {
            service.sendNotification(panicTO);
            if(service.wasRequestWithError())
            {
                result.setError(service.getLastErrorMessage());
            }
            else if(service.wasRequestWithMessage())
            {
                result.setResult(service.getLastMessage());
            }
        }
        catch (Exception e)
        {
            result.setError("Unable to send alarm");
        }
        return result;
    }

    @Override
    protected Class getTransferObjectType() {
        return PanicTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.panicTO = (PanicTO) transferObject;
    }
}
