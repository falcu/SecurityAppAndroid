package com.example.guido.securityapp.models;

import java.util.List;
import java.util.Stack;

/**
 * Created by guido on 2/21/15.
 */
public class NotificationsHistory {

    private Stack<Notification> alarms;

    public NotificationsHistory()
    {
        alarms = new Stack<>();
    }

    public void addAlarm(Notification a)
    {
        alarms.push(a);
    }

    public List<Notification> getAlarms()
    {
        return alarms;
    }
}
