package com.example.guido.securityapp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    protected ImageView warningIcon;


    public LastAlarmFragment() {
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.subscribe(this, MyApplication.getContext().getResources().getString(R.string.UPDATE_LAST_ALARM));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_last_alarm, container, false);
        senderDetails = (TextView) theView.findViewById(R.id.notification_sender);
        message = (TextView) theView.findViewById(R.id.notification_message);
        viewLocationAction = (ImageButton) theView.findViewById(R.id.view_location_action);
        viewLocationAction.setOnClickListener(this);
        warningIcon = (ImageView) theView.findViewById(R.id.warning_sign);
        warningIcon.setBackgroundResource(R.drawable.warning_animation);
        warningIcon.setVisibility(View.INVISIBLE);
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
        if(lastAlarm.getWasSeen())
        {
            warningIcon.setVisibility(View.INVISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) warningIcon.getBackground();
            animationDrawable.stop();
        }
        else
        {
            warningIcon.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) warningIcon.getBackground();
            animationDrawable.start();
        }

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
        try {
            if(!lastAlarm.getWasSeen())
                BuilderServiceAlarmStore.build(BuilderServiceAlarmStore.NotificationType.ALARM).markNotificationAsSeen(lastAlarm);
        } catch (Exception e) {
            //TODO HANDLE
        }
        getActivity().startActivity(i);
    }
}
