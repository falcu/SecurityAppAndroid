package com.example.guido.securityapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.models.Notification;
import com.example.guido.securityapp.models.NotificationsHistory;
import com.example.guido.securityapp.services.ServiceAlarmStore;

/**
 * Created by guido on 2/22/15.
 */
public class AlarmHistoryAdapter extends BaseAdapter implements ISubscriber, AdapterView.OnItemClickListener{

    protected ImageView yesIcon;
    protected NotificationsHistory notificationHistory;
    protected IEventAggregator eventAggregator;
    protected Activity activity;
    protected ServiceAlarmStore serviceAlarmStore;

    public AlarmHistoryAdapter(NotificationsHistory notificationHistory, IEventAggregator eventAggregator, Activity activity, String listenerKey,ServiceAlarmStore serviceAlarmStore) {
        this.notificationHistory = notificationHistory;
        this.eventAggregator = eventAggregator;
        this.activity = activity;
        this.eventAggregator.subscribe(this, listenerKey);
        this.serviceAlarmStore = serviceAlarmStore;
    }

    @Override
    public int getCount() {
        return notificationHistory.getAlarms().size();
    }

    @Override
    public Object getItem(int position) {
        return notificationHistory.getAlarms().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notifications_history_layout, parent, false);
        }

        Notification currentItem = notificationHistory.getAlarms().get(position);
        TextView sender = (TextView) convertView.findViewById(R.id.notification_sender);
        TextView message = (TextView) convertView.findViewById(R.id.notification_message);
        yesIcon = (ImageView) convertView.findViewById(R.id.yes_icon);
        ImageView image = (ImageView) convertView.findViewById(R.id.notification_icon);

        setIcon(image);
        sender.setText(currentItem.getSender().getName()+"("+currentItem.getSender().getEmail()+")");
        message.setText(currentItem.getMessage());
        showYesIconIfSeen(currentItem);


        return convertView;
    }

    protected void showYesIconIfSeen(Notification notification)
    {
        if(notification.getWasSeen())
        {
            yesIcon.setVisibility(View.VISIBLE);
        }
        else
        {
            yesIcon.setVisibility(View.INVISIBLE);
        }
    }

    protected void setIcon(ImageView imageView)
    {
        imageView.setImageDrawable(MyApplication.getContext().getResources().getDrawable(R.drawable.panic_icon_small));
    }

    @Override
    public void onEvent(final Object data) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notificationHistory = (NotificationsHistory) data;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notification notification =  notificationHistory.getAlarms().get(position);
        String url = notification.getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        try {
            if(!notification.getWasSeen())
             serviceAlarmStore.markNotificationAsSeen(notification);
        } catch (Exception e) {
            //TODO HANDLE EXCEPTION
        }
        activity.startActivity(i);
    }
}
