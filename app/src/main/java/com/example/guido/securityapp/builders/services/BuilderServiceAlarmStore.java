package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.converters.json.ObjectToJson;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;
import com.example.guido.securityapp.services.ServiceAlarmStore;
import com.example.guido.securityapp.services.ServiceLocalityNotificationStore;
import com.example.guido.securityapp.services.ServiceStore;

/**
 * Created by guido on 2/21/15.
 */
public class BuilderServiceAlarmStore {

    public static ServiceAlarmStore serviceAlarmStore = null;
    public static ServiceAlarmStore serviceLocalityNotification = null;

    public static ServiceAlarmStore build(NotificationType type)
    {
        ServiceAlarmStore service = null;
        switch (type)
        {
            case ALARM:
                if(serviceAlarmStore == null)
                {
                    Converter converter = new JsonToObject(Notification.class);
                    String key = MyApplication.getContext().getResources().getString(R.string.alarms_store_key);
                    ServiceStore serviceStore = new ServiceStore(key,new JsonToObject(NotificationsHistory.class),new ObjectToJson());
                    IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
                    serviceAlarmStore = new ServiceAlarmStore(serviceStore,converter,eventAggregator);
                }
                service = serviceAlarmStore;
                break;
            case LOCALITY_NOTIFICATION:
                if(serviceLocalityNotification ==null)
                {
                    Converter converter = new JsonToObject(Notification.class);
                    String key = MyApplication.getContext().getResources().getString(R.string.locality_notifications_store_key);
                    ServiceStore serviceStore = new ServiceStore(key,new JsonToObject(NotificationsHistory.class),new ObjectToJson());
                    IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
                    serviceLocalityNotification = new ServiceLocalityNotificationStore(serviceStore,converter,eventAggregator);
                }
                service = serviceLocalityNotification;
                break;

        }

        return service;
    }

    public enum NotificationType
    {
        ALARM,LOCALITY_NOTIFICATION
    }
}
