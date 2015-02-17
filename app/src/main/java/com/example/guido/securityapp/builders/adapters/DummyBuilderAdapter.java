package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.adapters.GroupMembersAdapter;
import com.example.guido.securityapp.adapters.MemberListItem;
import com.example.guido.securityapp.interfaces.IBuildAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/16/15.
 */
public class DummyBuilderAdapter implements IBuildAdapter{
    private Activity activity;

    public DummyBuilderAdapter(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public BaseAdapter build() {
        List<MemberListItem> items = new ArrayList<>();
        items.add(new MemberListItem("guido","guido@hotmail.com", MyApplication.getContext().getResources().getDrawable(R.drawable.member_icon)));
        items.add(new MemberListItem("juan","juan@hotmail.com", MyApplication.getContext().getResources().getDrawable(R.drawable.member_icon)));

        GroupMembersAdapter adapter = new GroupMembersAdapter(items,activity);

        return adapter;
    }
}
