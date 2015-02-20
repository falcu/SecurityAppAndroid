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
import com.example.guido.securityapp.services.ServiceGroupMember;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/8/15.
 */
public class FactoryGroupConfigurationFragments
{
    private Activity activity;
    private HashMap<String,BaseFragmentOption> creatorFragmentsByKey;
    private HashMap<String,BaseFragmentOption> regularMemberFragmentByKey;

    public FactoryGroupConfigurationFragments(Activity activity)
    {
        this.activity = activity;
        creatorFragmentsByKey = new HashMap<>();
        regularMemberFragmentByKey = new HashMap<>();
    }

    public void addFragmentsToManager() throws Exception
    {
        initializeIfNecessary();
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

    private void initializeIfNecessary() throws Exception
    {
        if(creatorFragmentsByKey.isEmpty() && regularMemberFragmentByKey.isEmpty())
        {
            if(isCreator())
            {
                addMemberFragment(creatorFragmentsByKey);
                removeMemberFragment(creatorFragmentsByKey);
                renameGroupFragment(creatorFragmentsByKey);
                quitGroupFragment(creatorFragmentsByKey);
            }
            else
            {
                quitGroupFragment(regularMemberFragmentByKey);
            }
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

    private boolean isCreator() throws Exception {
        return BuilderServiceGroupMember.build().IsCurrentUserCreator();
    }

    private void addMemberFragment(HashMap<String,BaseFragmentOption> fragments)
    {
        String key = MyApplication.getContext().getResources().getString(R.string.add_member_action_key);

        BaseFragmentOption maybeFragment = tryToGetFragmentFromManager(key);

        if(maybeFragment == null)
        {
            Integer id = 1;
            AddMemberFragment addMemberFragment = new AddMemberFragment();
            addMemberFragment.setIdentifier(id);
            addMemberFragment.setKey(key);
            addMemberFragment.setDescription("Add member");
            fragments.put(key,addMemberFragment);
            addFragmentToManager(addMemberFragment);
        }
        else
        {
            fragments.put(key,maybeFragment);
        }
    }



    private void removeMemberFragment(HashMap<String,BaseFragmentOption> fragments)
    {
        String key = MyApplication.getContext().getResources().getString(R.string.remove_member_action_key);
        BaseFragmentOption maybeFragment = tryToGetFragmentFromManager(key);

        if(maybeFragment==null)
        {
            Integer id = 2;
            RemoveMemberFragment removeMemberFragment = new RemoveMemberFragment();
            removeMemberFragment.setIdentifier(id);
            removeMemberFragment.setKey(key);
            removeMemberFragment.setDescription("Remove member");
            fragments.put(key,removeMemberFragment);
            addFragmentToManager(removeMemberFragment);
        }
        else
        {
            fragments.put(key,maybeFragment);
        }
    }

    private void renameGroupFragment(HashMap<String,BaseFragmentOption> fragments)
    {
        String key = MyApplication.getContext().getResources().getString(R.string.rename_group_action_key);
        BaseFragmentOption maybeFragment = tryToGetFragmentFromManager(key);

        if(maybeFragment==null) {

            Integer id = 3;
            RenameGroupFragment renameGroupFragment = new RenameGroupFragment();
            renameGroupFragment.setIdentifier(id);
            renameGroupFragment.setKey(key);
            renameGroupFragment.setDescription("Rename group");
            fragments.put(key,renameGroupFragment);
            addFragmentToManager(renameGroupFragment);
        }
        else
        {
            fragments.put(key,maybeFragment);
        }

    }

    private void quitGroupFragment(HashMap<String,BaseFragmentOption> fragments)
    {
        String key = MyApplication.getContext().getResources().getString(R.string.quit_group_action_key);
        BaseFragmentOption maybeFragment = tryToGetFragmentFromManager(key);

        if(maybeFragment==null)
        {
            Integer id = 4;
            QuitGroupFragment quitGroupFragment = new QuitGroupFragment();
            quitGroupFragment.setIdentifier(id);
            quitGroupFragment.setKey(key);
            quitGroupFragment.setDescription("Quit group");
            fragments.put(key,quitGroupFragment);
            addFragmentToManager(quitGroupFragment);
        }
        else
        {
            fragments.put(key,maybeFragment);
        }

    }

    private BaseFragmentOption tryToGetFragmentFromManager(String tag)
    {
        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment maybeFragment = fragmentManager.findFragmentByTag(tag);
        if(maybeFragment==null){
            return null;
        }
        else
        {
            return(BaseFragmentOption)maybeFragment;
        }
    }

    private void addFragmentToManager(BaseFragmentOption fragment)
    {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.group_configuration_options,fragment,fragment.getKey());
        fragmentTransaction.commit();
    }


}