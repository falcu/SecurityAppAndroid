package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceNotification;
import com.example.guido.securityapp.models.NotificationTO;
import com.example.guido.securityapp.services.ServiceNotification;

/**
 * Created by guido on 3/7/15.
 */
public class NotificationTask extends AlarmTask{
    public NotificationTask(NotificationTO notificationTO) throws Exception {
        super(notificationTO);
    }

    @Override
    protected ServiceNotification makeService() {
        return BuilderServiceNotification.build(BuilderServiceNotification.NotificationType.NOTIFICATION);
    }


}
