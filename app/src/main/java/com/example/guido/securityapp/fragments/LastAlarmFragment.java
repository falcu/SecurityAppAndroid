package com.example.guido.securityapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceAlarmStore;
import com.example.guido.securityapp.exceptions.NotRecentAlarm;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.models.Notification;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastAlarmFragment extends Fragment implements ISubscriber,View.OnClickListener {

    protected Notification lastAlarm = null;
    protected View alarmDetailsView;
    protected View noAlarmView;
    protected TextView senderDetails;
    protected TextView message;
    protected IEventAggregator eventAggregator;
    protected ImageButton viewLocationAction;


    public LastAlarmFragment() {
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.Subscribe(this, MyApplication.getContext().getResources().getString(R.string.UPDATE_LAST_ALARM));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_last_alarm, container, false);
        senderDetails = (TextView) theView.findViewById(R.id.alarm_sender);
        message = (TextView) theView.findViewById(R.id.alarm_message);
        viewLocationAction = (ImageButton) theView.findViewById(R.id.view_location_action);
        viewLocationAction.setOnClickListener(this);
        noAlarmView = theView.findViewById(R.id.no_last_alarm_view);
        alarmDetailsView = theView.findViewById(R.id.last_alarm_details);
        try
        {
            lastAlarm = BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM).getMostRecentNotification();
            updateView();
            showView(alarmDetailsView);
            hideView(noAlarmView);
        }
        catch (NotRecentAlarm e)
        {
            showView(noAlarmView);
            hideView(alarmDetailsView);
        }

        return theView;
    }

    private void showView(View v)
    {
        v.setVisibility(View.VISIBLE);
    }

    private void hideView(View v)
    {
        v.setVisibility(View.GONE);
    }

    private void updateView()
    {
        senderDetails.setText(lastAlarm.getSender().getName());
        message.setText(lastAlarm.getMessage());
    }


    @Override
    public void onEvent(Object data) {
        lastAlarm = (Notification) data;
        Activity currentActivity = getActivity();
        if(currentActivity!=null)
        {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateView();
                    showView(alarmDetailsView);
                    hideView(noAlarmView);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        String url = lastAlarm.getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        getActivity().startActivity(i);
    }
}
