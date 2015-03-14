package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.commands.NotificationReceivedCommand;
import com.example.guido.securityapp.commands.GroupChangedGcmCommand;
import com.example.guido.securityapp.converters.json.GcmGroupToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.services.ServiceAlarmStore;
import com.example.guido.securityapp.services.ServiceStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/22/15.
 */
public class GcmHelper {

    private List<GcmHandler> gcmHandlers;
    private IntentService intentService;
    private Intent intent;

    public GcmHelper(IntentService intentService,Intent intent)
    {
        this.intentService = intentService;
        this.intent = intent;
        initialize();
    }

    public NotificationCompat.Builder getNotificationBuilder()
    {
        return getHandler().build();
    }

    public void handleGcmMessage()
    {
        getHandler().handle();
    }

    private void initialize()
    {
        gcmHandlers = new ArrayList<>();
        IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
        GcmParser parser = new GcmParser(intent);

        IDataStore groupStore = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new GcmGroupToJson());
        GroupGcmHandler groupHandler = new GroupGcmHandler(parser,intent,intentService,new GroupChangedGcmCommand(intent,eventAggregator,groupStore));

        ServiceAlarmStore serviceAlarmStore = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM);
        NotificationReceivedCommand alarmReceivedCommand = new NotificationReceivedCommand(intent, serviceAlarmStore,"alarm_info");
        AlarmGcmHandler alarmHandler = new AlarmGcmHandler(parser,intent,intentService,alarmReceivedCommand);

        ServiceAlarmStore serviceLocalityNotificationStore = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.LOCALITY_NOTIFICATION);
        NotificationReceivedCommand localityNotificationReceivedCommand = new NotificationReceivedCommand(intent, serviceLocalityNotificationStore,"locality_notification_info");
        LocalityNotificationGcmHandler localityNotificationGcmHandler = new LocalityNotificationGcmHandler(parser,intent,intentService,localityNotificationReceivedCommand);

        gcmHandlers.add(groupHandler);
        gcmHandlers.add(alarmHandler);
        gcmHandlers.add(localityNotificationGcmHandler);
    }

    private GcmHandler getHandler()
    {
        boolean handlerFound = false;
        int i = 0;
        while(!handlerFound && i<gcmHandlers.size())
        {
            if(gcmHandlers.get(i).canHandle())
            {
                handlerFound = true;

            }
            else
            {
                i++;
            }
        }

        return gcmHandlers.get(i);
    }
}
