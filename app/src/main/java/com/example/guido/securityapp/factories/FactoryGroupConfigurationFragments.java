package com.example.guido.securityapp.factories;

import android.app.Fragment;

import com.example.guido.securityapp.fragments.AddMemberFragment;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.RemoveMemberFragment;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/8/15.
 */
public class FactoryGroupConfigurationFragments
{
    private static HashMap<Integer,BaseFragmentOption> creatorFragmentsById;
    private static HashMap<String,BaseFragmentOption> creatorFragmentsByKey;


    public static Iterator<BaseFragmentOption> getCreatorFragments()
    {
        checkIfInitialize();
        return creatorFragmentsById.values().iterator();
    }

    public static BaseFragmentOption getFragmentByKey(String key)
    {
        checkIfInitialize();
        return creatorFragmentsByKey.get(key);
    }

    public static void clean()
    {
        creatorFragmentsById = null;
        creatorFragmentsByKey = null;
    }

    private static void checkIfInitialize()
    {
        if(creatorFragmentsById == null || creatorFragmentsByKey == null)
        {
            clean();
            initialize();

        }
    }

    private static void initialize()
    {
        creatorFragmentsById = new HashMap<>();
        creatorFragmentsByKey = new HashMap<>();
        addMemberFragment();
        removeMemberFragment();

    }

    private static void addMemberFragment()
    {
        String key = "add_member";
        Integer id = 1;
        AddMemberFragment addMemberFragment = new AddMemberFragment();
        addMemberFragment.setIdentifier(id);
        addMemberFragment.setKey(key);
        addMemberFragment.setDescription("Add member");
        creatorFragmentsById.put(id,addMemberFragment);
        creatorFragmentsByKey.put(key,addMemberFragment);
    }

    private static void removeMemberFragment()
    {
        String key = "remove_member";
        Integer id = 2;
        RemoveMemberFragment removeMemberFragment = new RemoveMemberFragment();
        removeMemberFragment.setIdentifier(id);
        removeMemberFragment.setKey(key);
        removeMemberFragment.setDescription("Remove member");
        creatorFragmentsById.put(id,removeMemberFragment);
        creatorFragmentsByKey.put(key,removeMemberFragment);
    }


}