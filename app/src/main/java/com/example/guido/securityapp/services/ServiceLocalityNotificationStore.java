package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;

/**
 * Created by guido on 3/12/15.
 */
public class ServiceLocalityNotificationStore extends ServiceAlarmStore {
    public ServiceLocalityNotificationStore(IDataStore store, Converter converter, IEventAggregator eventAggregator) {
        super(store, converter, eventAggregator);
    }

    @Override
    protected void update(NotificationsHistory notificationsHistory, Notification notification) {
        eventAggregator.publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_LOCALITY_NOTIFICATIONS), notificationsHistory);
    }

    @Override
    protected void updateMarkedNotification(NotificationsHistory notificationsHistory, Notification updatedNotification) {
        update(notificationsHistory,updatedNotification);
    }
}
