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
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.models.Alarm;
import com.example.guido.securityapp.models.AlarmsHistory;

/**
 * Created by guido on 2/22/15.
 */
public class AlarmsHistoryAdapter extends BaseAdapter implements ISubscriber, AdapterView.OnItemClickListener{

    private AlarmsHistory alarmsHistory;
    private IEventAggregator eventAggregator;
    private Activity activity;

    public AlarmsHistoryAdapter(AlarmsHistory alarmsHistory, IEventAggregator eventAggregator, Activity activity) {
        this.alarmsHistory = alarmsHistory;
        this.eventAggregator = eventAggregator;
        this.activity = activity;
        this.eventAggregator.Subscribe(this, MyApplication.getContext().getResources().getString(R.string.UPDATE_ALARM));
    }

    @Override
    public int getCount() {
        return alarmsHistory.getAlarms().size();
    }

    @Override
    public Object getItem(int position) {
        return alarmsHistory.getAlarms().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarms_history_layout, parent, false);
        }

        Alarm currentItem = alarmsHistory.getAlarms().get(position);
        TextView sender = (TextView) convertView.findViewById(R.id.alarm_sender);
        TextView message = (TextView) convertView.findViewById(R.id.alarm_message);

        sender.setText(currentItem.getSender().getName()+"("+currentItem.getSender().getEmail()+")");
        message.setText(currentItem.getMessage());



        return convertView;
    }

    @Override
    public void onEvent(final Object data) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alarmsHistory = (AlarmsHistory) data;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String url = alarmsHistory.getAlarms().get(position).getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }
}
