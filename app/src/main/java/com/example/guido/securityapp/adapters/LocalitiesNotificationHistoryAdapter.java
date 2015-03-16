package com.example.guido.securityapp.adapters;

import android.app.Activity;
import android.widget.ImageView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.NotificationsHistory;
import com.example.guido.securityapp.services.ServiceAlarmStore;

/**
 * Created by guido on 3/14/15.
 */
public class LocalitiesNotificationHistoryAdapter extends AlarmHistoryAdapter {

    public LocalitiesNotificationHistoryAdapter(NotificationsHistory notificationHistory, IEventAggregator eventAggregator, Activity activity, String listenerKey, ServiceAlarmStore serviceAlarmStore) {
        super(notificationHistory, eventAggregator, activity, listenerKey, serviceAlarmStore);
    }

    @Override
    protected void setIcon(ImageView imageView) {
        imageView.setImageDrawable(MyApplication.getContext().getResources().getDrawable(R.drawable.locality_icon));
    }
}
