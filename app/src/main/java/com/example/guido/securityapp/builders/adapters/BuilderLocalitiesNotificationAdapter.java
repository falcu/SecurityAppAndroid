package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.adapters.AlarmHistoryAdapter;
import com.example.guido.securityapp.adapters.LocalitiesNotificationHistoryAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.NotificationsHistory;

/**
 * Created by guido on 3/14/15.
 */
public class BuilderLocalitiesNotificationAdapter implements IBuildAdapter {

    protected Activity activity;

    public BuilderLocalitiesNotificationAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseAdapter buildAdapter() throws Exception {
        NotificationsHistory alarmsHistory = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.LOCALITY_NOTIFICATION).getNotificationsHistory();
        IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
        String listenerKey = MyApplication.getContext().getResources().getString(R.string.UPDATE_LOCALITY_NOTIFICATIONS);
        LocalitiesNotificationHistoryAdapter adapter = new LocalitiesNotificationHistoryAdapter(alarmsHistory,eventAggregator,activity,listenerKey);

        return adapter;
    }
}
