package com.example.guido.securityapp.factories;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.fragments.BaseFragmentOption;

import java.util.Iterator;

/**
 * Created by guido on 3/8/15.
 */
public abstract class FactoryFragmentsOptions {

    protected Activity activity;
    protected int fragmentId = 0;

    protected FactoryFragmentsOptions(Activity activity) {
        this.activity = activity;
    }

    public abstract Iterator<BaseFragmentOption> getFragments() throws Exception;
    public abstract BaseFragmentOption getFragmentByKey(String key) throws Exception;

    protected BaseFragmentOption getFragment(String key)
    {
        BaseFragmentOption fragment = tryToGetFragmentFromManager(key);
        if(fragment==null)
        {
            fragment = makeFragment(key);
        }
        addFragmentToManager(fragment);

        return fragment;
    }

    protected abstract BaseFragmentOption makeFragment(String key);

    protected BaseFragmentOption tryToGetFragmentFromManager(String tag)
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

    protected void addFragmentToManager(BaseFragmentOption fragment)
    {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(getParentViewId(),fragment,fragment.getKey());
        fragmentTransaction.commit();
    }

    protected abstract int getParentViewId();

    protected int getNextId()
    {
        return fragmentId++;
    }
}
