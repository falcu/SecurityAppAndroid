package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceNotification;
import com.example.guido.securityapp.models.NotificationTO;
import com.example.guido.securityapp.services.ServiceNotification;

/**
 * Created by guido on 2/14/15.
 */
public class AlarmTask extends AsynTaskWithHandlers{
    protected NotificationTO notificationTO;

    public AlarmTask(NotificationTO notificationTO) throws Exception
    {
        super(notificationTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        ServiceNotification service = makeService();
        try
        {
            service.sendNotification(notificationTO);
            if(service.wasRequestWithError())
            {
                TaskError error = new TaskError(service.getLastErrorMessage());
                result.setError(error);
            }
            else if(service.wasRequestWithMessage())
            {
                result.setResult(service.getLastMessage());
            }
        }
        catch (Exception e)
        {
            result.setError(new TaskError("Unable to send notification"));
        }
        return result;
    }

    protected ServiceNotification makeService()
    {
        return BuilderServiceNotification.build(BuilderServiceNotification.NotificationType.ALARM);
    }

    @Override
    protected Class getTransferObjectType() {
        return NotificationTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.notificationTO = (NotificationTO) transferObject;
    }
}
