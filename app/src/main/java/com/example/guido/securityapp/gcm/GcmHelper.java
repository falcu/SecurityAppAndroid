package com.example.guido.securityapp.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarm;
import com.example.guido.securityapp.commands.AlarmReceivedCommand;
import com.example.guido.securityapp.commands.GroupChangedGcmCommand;
import com.example.guido.securityapp.converters.json.GcmGroupToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.services.ServiceAlarm;
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

        ServiceAlarm serviceAlarm = BuilderServiceAlarm.build();
        AlarmReceivedCommand alarmReceivedCommand = new AlarmReceivedCommand(intent,serviceAlarm);
        AlarmGcmHandler alarmHandler = new AlarmGcmHandler(parser,intent,intentService,alarmReceivedCommand);

        gcmHandlers.add(groupHandler);
        gcmHandlers.add(alarmHandler);
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
