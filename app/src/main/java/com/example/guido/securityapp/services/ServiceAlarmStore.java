package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.exceptions.MissingDataException;
import com.example.guido.securityapp.exceptions.NotRecentAlarm;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;

/**
 * Created by guido on 2/21/15.
 */
public class ServiceAlarmStore {

    protected IDataStore store;
    protected IEventAggregator eventAggregator;
    protected Converter converter;


    public ServiceAlarmStore(IDataStore store, Converter converter, IEventAggregator eventAggregator)
    {
        this.store = store;
        this.converter = converter;
        this.eventAggregator = eventAggregator;
    }

    public void saveNewNotification(String alarmJson)
    {
        try {
            Notification alarm = (Notification)converter.convert(alarmJson);
            NotificationsHistory alarmsHistory = loadNotifications();
            alarmsHistory.addAlarm(alarm);
            store.save(alarmsHistory);
            update(alarmsHistory,alarm);

        } catch (Exception e) {

        }
    }

    protected void update(NotificationsHistory notificationsHistory, Notification notification )
    {
        eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_ALARM),notificationsHistory);
        eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_LAST_ALARM),notification);
    }

    public NotificationsHistory getNotificationsHistory()
    {
        return loadNotifications();
    }

    public Notification getMostRecentNotification() throws NotRecentAlarm
    {
        NotificationsHistory alarmsHistory = loadNotifications();
        if(alarmsHistory.getAlarms().isEmpty())
        {
            throw new NotRecentAlarm("There is not an alarm stored yet");
        }

        return alarmsHistory.getAlarms().get(0);

    }

    private NotificationsHistory loadNotifications()
    {
        try
        {
            NotificationsHistory alarmsHistory = (NotificationsHistory) store.load();
            return alarmsHistory;
        }
        catch (MissingDataException e)
        {
            return new NotificationsHistory();
        }
        catch (Exception e)
        {
            return null;
            //TODO HANDLE GENERALE EXCEPTION
        }
    }
}
