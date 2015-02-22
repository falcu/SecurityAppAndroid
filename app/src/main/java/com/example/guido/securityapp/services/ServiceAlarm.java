package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.exceptions.MissingDataException;
import com.example.guido.securityapp.exceptions.NotRecentAlarm;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Alarm;
import com.example.guido.securityapp.models.AlarmsHistory;

/**
 * Created by guido on 2/21/15.
 */
public class ServiceAlarm {

    private IDataStore store;
    private IEventAggregator eventAggregator;
    private Converter converter;


    public ServiceAlarm(IDataStore store,Converter converter,IEventAggregator eventAggregator)
    {
        this.store = store;
        this.converter = converter;
        this.eventAggregator = eventAggregator;
    }

    public void saveNewAlarm(String alarmJson)
    {
        try {
            Alarm alarm = (Alarm)converter.convert(alarmJson);
            AlarmsHistory alarmsHistory = loadAlarms();
            alarmsHistory.addAlarm(alarm);
            store.save(alarmsHistory);
            eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_ALARM),alarmsHistory);
            eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_LAST_ALARM),alarm);

        } catch (Exception e) {

        }
    }

    public AlarmsHistory getAlarmsHistory()
    {
        return loadAlarms();
    }

    public Alarm getMostRecentAlarm() throws NotRecentAlarm
    {
        AlarmsHistory alarmsHistory = loadAlarms();
        if(alarmsHistory.getAlarms().isEmpty())
        {
            throw new NotRecentAlarm("There is not an alarm stored yet");
        }

        return alarmsHistory.getAlarms().get(0);

    }

    private AlarmsHistory loadAlarms()
    {
        try
        {
            AlarmsHistory alarmsHistory = (AlarmsHistory) store.load();
            return alarmsHistory;
        }
        catch (MissingDataException e)
        {
            return new AlarmsHistory();
        }
        catch (Exception e)
        {
            return null;
            //TODO HANDLE GENERALE EXCEPTION
        }
    }
}
