package com.example.guido.securityapp.factories;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.CreateGroupFragment;
import com.example.guido.securityapp.fragments.DescriptionFragment;
import com.example.guido.securityapp.fragments.JoinGroupFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 3/8/15.
 */
public class FactoryCreateGroupFragments extends FactoryFragmentsOptions{

    private HashMap<String,BaseFragmentOption> fragments;
    private List<String> fragmentsKeys;

    public FactoryCreateGroupFragments(Activity activity) {
       super(activity);
        fragments = new HashMap<>();
        fragmentsKeys = new ArrayList<>();
        initializeKeys();
    }

    private void initializeKeys()
    {
        fragmentsKeys.add(MyApplication.getContext().getResources().getString(R.string.create_group_key));
        fragmentsKeys.add(MyApplication.getContext().getResources().getString(R.string.join_group_key));
    }

    @Override
    public Iterator<BaseFragmentOption> getFragments()
    {
        initializeIfNecessary();
        return fragments.values().iterator();
    }

    @Override
    public BaseFragmentOption getFragmentByKey(String key)
    {
        initializeIfNecessary();
        return fragments.get(key);
    }

    private void initializeIfNecessary()
    {
        if(fragments.isEmpty())
        {
            for (String key: fragmentsKeys)
            {
                BaseFragmentOption fragment = getFragment(key);
                fragments.put(key,fragment);
            }
        }
    }

    @Override
    protected BaseFragmentOption makeFragment(String key)
    {
        BaseFragmentOption fragment = null;
        int id = getNextId();
        String description = null;
        if(key.equals(MyApplication.getContext().getResources().getString(R.string.create_group_key)))
        {
            description = MyApplication.getContext().getResources().getString(R.string.create_group_text);
            fragment = new CreateGroupFragment();

        }
        else if(key.equals(MyApplication.getContext().getResources().getString(R.string.join_group_key)))
        {
            description = MyApplication.getContext().getResources().getString(R.string.join_group_text);
            fragment = new JoinGroupFragment();
            ((JoinGroupFragment)fragment).setMessageToUser(MyApplication.getContext().getResources().getString(R.string.join_group_description));
        }
        fragment.setIdentifier(id);
        fragment.setKey(key);
        fragment.setDescription(description);

        return fragment;
    }

    @Override
    protected int getParentViewId() {
        return R.id.create_group_options;
    }

}
