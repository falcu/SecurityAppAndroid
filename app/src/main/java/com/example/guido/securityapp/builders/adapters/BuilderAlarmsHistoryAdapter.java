package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.adapters.AlarmsHistoryAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarm;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.AlarmsHistory;

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
        AlarmsHistory alarmsHistory = BuilderServiceAlarm.build().getAlarmsHistory();
        IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();
        AlarmsHistoryAdapter adapter = new AlarmsHistoryAdapter(alarmsHistory,eventAggregator,activity);

        return adapter;
    }
}
