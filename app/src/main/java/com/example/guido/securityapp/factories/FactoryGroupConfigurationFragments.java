package com.example.guido.securityapp.factories;

import android.app.Fragment;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.fragments.AddMemberFragment;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.QuitGroupFragment;
import com.example.guido.securityapp.fragments.RemoveMemberFragment;
import com.example.guido.securityapp.fragments.RenameGroupFragment;

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
        renameGroupFragment();
        quitGroupFragment();

    }

    private static void addMemberFragment()
    {
        String key = MyApplication.getContext().getResources().getString(R.string.add_member_action_key);
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
        String key = MyApplication.getContext().getResources().getString(R.string.remove_member_action_key);
        Integer id = 2;
        RemoveMemberFragment removeMemberFragment = new RemoveMemberFragment();
        removeMemberFragment.setIdentifier(id);
        removeMemberFragment.setKey(key);
        removeMemberFragment.setDescription("Remove member");
        creatorFragmentsById.put(id,removeMemberFragment);
        creatorFragmentsByKey.put(key,removeMemberFragment);
    }

    private static void renameGroupFragment()
    {
        String key = MyApplication.getContext().getResources().getString(R.string.rename_group_action_key);
        Integer id = 3;
        RenameGroupFragment renameGroupFragment = new RenameGroupFragment();
        renameGroupFragment.setIdentifier(id);
        renameGroupFragment.setKey(key);
        renameGroupFragment.setDescription("Rename group");
        creatorFragmentsById.put(id,renameGroupFragment);
        creatorFragmentsByKey.put(key,renameGroupFragment);
    }

    private static void quitGroupFragment()
    {
        String key = MyApplication.getContext().getResources().getString(R.string.quit_group_action_key);;
        Integer id = 4;
        QuitGroupFragment quitGroupFragment = new QuitGroupFragment();
        quitGroupFragment.setIdentifier(id);
        quitGroupFragment.setKey(key);
        quitGroupFragment.setDescription("Quit group");
        creatorFragmentsById.put(id,quitGroupFragment);
        creatorFragmentsByKey.put(key,quitGroupFragment);
    }


}