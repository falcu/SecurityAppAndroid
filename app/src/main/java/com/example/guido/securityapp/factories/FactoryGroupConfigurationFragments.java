package com.example.guido.securityapp.factories;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceGroupMember;
import com.example.guido.securityapp.fragments.AddMemberFragment;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.QuitGroupFragment;
import com.example.guido.securityapp.fragments.RemoveMemberFragment;
import com.example.guido.securityapp.fragments.RenameGroupFragment;
import com.example.guido.securityapp.fragments.SynchronizeExistentGroupFragment;
import com.example.guido.securityapp.services.ServiceGroupMember;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/8/15.
 */
public class FactoryGroupConfigurationFragments extends FactoryFragmentsOptions
{
    private HashMap<String,BaseFragmentOption> creatorFragmentsByKey;
    private HashMap<String,BaseFragmentOption> regularMemberFragmentByKey;
    private List<String> creatorKeys;
    private List<String> regularMemberKeys;

    public FactoryGroupConfigurationFragments(Activity activity)
    {
        super(activity);
        creatorFragmentsByKey = new HashMap<>();
        regularMemberFragmentByKey = new HashMap<>();
        creatorKeys = new ArrayList<>();
        regularMemberKeys = new ArrayList<>();
        initializeKeys();
    }

    private void initializeKeys()
    {
        creatorKeys.add(MyApplication.getContext().getResources().getString(R.string.add_member_action_key));
        creatorKeys.add(MyApplication.getContext().getResources().getString(R.string.remove_member_action_key));
        creatorKeys.add(MyApplication.getContext().getResources().getString(R.string.rename_group_action_key));
        creatorKeys.add(MyApplication.getContext().getResources().getString(R.string.quit_group_action_key));
        creatorKeys.add(MyApplication.getContext().getResources().getString(R.string.refresh_group_action_key));

        regularMemberKeys.add(MyApplication.getContext().getResources().getString(R.string.quit_group_action_key));
        regularMemberKeys.add(MyApplication.getContext().getResources().getString(R.string.refresh_group_action_key));
    }

    public Iterator<BaseFragmentOption> getFragments() throws Exception
    {
        initializeIfNecessary();
        if(isCreator())
        {
            return creatorFragmentsByKey.values().iterator();
        }
        else
        {
            return regularMemberFragmentByKey.values().iterator();
        }
    }


    public BaseFragmentOption getFragmentByKey(String key) throws Exception
    {
        initializeIfNecessary();
        if(isCreator())
        {
            return creatorFragmentsByKey.get(key);
        }
        else
        {
            return regularMemberFragmentByKey.get(key);
        }
    }

    private void initializeIfNecessary() throws Exception
    {
        if(creatorFragmentsByKey.isEmpty() && regularMemberFragmentByKey.isEmpty())
        {
            addFragments(creatorFragmentsByKey,creatorKeys);
            addFragments(regularMemberFragmentByKey,regularMemberKeys);
        }
    }

    private void addFragments(HashMap<String,BaseFragmentOption> mapToAdd,List<String> keys)
    {
        for(String key : keys)
        {
            BaseFragmentOption fragment = getFragment(key);
            mapToAdd.put(key,fragment);
        }
    }


    @Override
    protected BaseFragmentOption makeFragment(String key) {
        BaseFragmentOption fragment = null;
        int id = getNextId();
        String description = null;

        if(key.equals(MyApplication.getContext().getResources().getString(R.string.add_member_action_key)))
        {
            fragment = new AddMemberFragment();
            description = MyApplication.getContext().getResources().getString(R.string.add_member_action_description);
        }
        else if(key.equals(MyApplication.getContext().getResources().getString(R.string.remove_member_action_key)))
        {
            fragment = new RemoveMemberFragment();
            description = MyApplication.getContext().getResources().getString(R.string.remove_member_action_description);
        }
        else if(key.equals(MyApplication.getContext().getResources().getString(R.string.rename_group_action_key)))
        {
            fragment = new RenameGroupFragment();
            description = MyApplication.getContext().getResources().getString(R.string.rename_group_action_description);
        }
        else if(key.equals(MyApplication.getContext().getResources().getString(R.string.quit_group_action_key)))
        {
            fragment = new QuitGroupFragment();
            description = MyApplication.getContext().getResources().getString(R.string.quit_group_action_description);
        }
        else if(key.equals(MyApplication.getContext().getResources().getString(R.string.refresh_group_action_key)))
        {
            fragment = new SynchronizeExistentGroupFragment();
            description = MyApplication.getContext().getResources().getString(R.string.refresh_group_action_description);
        }

        fragment.setIdentifier(id);
        fragment.setKey(key);
        fragment.setDescription(description);

        return fragment;
    }

    private boolean isCreator() throws Exception {
        return BuilderServiceGroupMember.build().IsCurrentUserCreator();
    }

    @Override
    protected int getParentViewId() {
        return R.id.group_configuration_options;
    }


}