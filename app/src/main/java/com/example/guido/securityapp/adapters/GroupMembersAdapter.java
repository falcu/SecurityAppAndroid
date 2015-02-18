package com.example.guido.securityapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.models.Group;

import java.util.List;

/**
 * Created by guido on 2/16/15.
 */
public class GroupMembersAdapter extends BaseAdapter implements ISubscriber {

    private List<MemberListItem> sourceData;
    private Activity activity;
    private Converter groupToMembersConverter;
    private IEventAggregator eventAggregator;

    public GroupMembersAdapter(Group group,Converter groupToMembersConverter,Activity activity,IEventAggregator eventAggregator)
    {
        this.activity = activity;
        this.groupToMembersConverter = groupToMembersConverter;
        this.eventAggregator = eventAggregator;
        eventAggregator.Subscribe(this, MyApplication.getContext().getResources().getString(R.string.group_updated_event));
        updateSourceData(group);
    }

    @Override
    public int getCount() {
        return sourceData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_members_layout, parent, false);
        }
            ImageView icon = (ImageView) convertView.findViewById(R.id.member_icon);
            TextView name = (TextView) convertView.findViewById(R.id.member_name);
            TextView email = (TextView) convertView.findViewById(R.id.member_email);
            MemberListItem currentItem = sourceData.get(position);

            icon.setImageDrawable(currentItem.getIcon());
            name.setText(currentItem.getName());
            email.setText(currentItem.getEmail());

        return convertView;
    }

    @Override
    public void onEvent(final Object data) {
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Group group = (Group)data;
                updateSourceData(group);
                notifyDataSetChanged();
            }
        });
    }

    public void updateSourceData(Group group)
    {
        try
        {
           if(sourceData!=null){sourceData.clear();}
            sourceData = ( List<MemberListItem>)groupToMembersConverter.convert(group);
        }
        catch (Exception e){
        }
    }
}
