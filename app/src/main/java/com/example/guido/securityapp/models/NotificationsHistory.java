package com.example.guido.securityapp.models;

import android.text.format.Time;

import java.util.Collections;
import java.util.Comparator;
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

    public void sortFromRecentToOldest()
    {
        Collections.sort(alarms,new NotificationByDateComparator());
    }

    public class NotificationByDateComparator implements Comparator<Notification>
    {
        @Override
        public int compare(Notification n1, Notification n2) {
            return Time.compare(n2.getReceivedTime(), n1.getReceivedTime());
        }
    }
}
