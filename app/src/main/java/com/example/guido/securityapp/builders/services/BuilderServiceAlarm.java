package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.converters.json.ObjectToJson;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Alarm;
import com.example.guido.securityapp.models.AlarmsHistory;
import com.example.guido.securityapp.services.ServiceAlarm;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 2/21/15.
 */
public class BuilderServiceAlarm {

    public static ServiceAlarm serviceAlarm = null;

    public static ServiceAlarm build()
    {
        if(serviceAlarm==null)
        {
            Converter converter = new JsonToObject(Alarm.class);
            String alarmStoreKey = MyApplication.getContext().getResources().getString(R.string.alarms_store_key);
            ServiceStore serviceStore = new ServiceStore(alarmStoreKey,new JsonToObject(AlarmsHistory.class),new ObjectToJson());
            IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
            serviceAlarm = new ServiceAlarm(serviceStore,converter,eventAggregator);
        }
        return serviceAlarm;
    }
}
