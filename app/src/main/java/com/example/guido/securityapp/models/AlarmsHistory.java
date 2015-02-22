package com.example.guido.securityapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by guido on 2/21/15.
 */
public class AlarmsHistory {

    private Stack<Alarm> alarms;

    public AlarmsHistory()
    {
        alarms = new Stack<>();
    }

    public void addAlarm(Alarm a)
    {
        alarms.push(a);
    }

    public List<Alarm> getAlarms()
    {
        return alarms;
    }
}
