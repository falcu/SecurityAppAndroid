package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.adapters.GroupMembersAdapter;
import com.example.guido.securityapp.adapters.MemberListItem;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.converters.GroupToMemberListConverter;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/16/15.
 */
public class BuilderGroupMembersAdapter implements IBuildAdapter{

    protected Activity activity;

    public BuilderGroupMembersAdapter(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public BaseAdapter build() throws Exception{
        Group group = BuilderServiceGroup.buildGroupInformationService().getGroup();

        return new GroupMembersAdapter(group,new GroupToMemberListConverter(),activity, FactoryEventAggregator.getInstance());

    }
}
