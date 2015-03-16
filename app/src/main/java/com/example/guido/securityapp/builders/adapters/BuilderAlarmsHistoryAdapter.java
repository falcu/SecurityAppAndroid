package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.adapters.AlarmHistoryAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.NotificationsHistory;
import com.example.guido.securityapp.services.ServiceAlarmStore;

/**
 * Created by guido on 2/22/15.
 */
public class BuilderAlarmsHistoryAdapter implements IBuildAdapter{

    protected Activity activity;

    public BuilderAlarmsHistoryAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseAdapter buildAdapter() throws Exception {
        NotificationsHistory alarmsHistory = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM).getNotificationsHistory();
        IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
        String listenerKey = MyApplication.getContext().getResources().getString(R.string.UPDATE_ALARM);
        ServiceAlarmStore serviceAlarmStore = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM);
        AlarmHistoryAdapter adapter = new AlarmHistoryAdapter(alarmsHistory,eventAggregator,activity,listenerKey,serviceAlarmStore);

        return adapter;
    }
}
