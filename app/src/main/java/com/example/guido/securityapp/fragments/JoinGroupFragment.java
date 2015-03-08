package com.example.guido.securityapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guido on 3/8/15.
 */
public class JoinGroupFragment extends BaseFragmentOption{

    protected DescriptionFragment helperFragment;
    protected String userMessage = null;

    public JoinGroupFragment() {
        helperFragment = new DescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView = helperFragment.onCreateView(inflater,container,savedInstanceState);
        theView.setVisibility(View.GONE);
        if(userMessage!=null)
            helperFragment.setDescription(userMessage);
        return theView;
    }

    public void setMessageToUser(String text)
    {
        if(getView()!=null)
        {
            helperFragment.setDescription(text);
        }
        else
        {
            userMessage = text;
        }
    }
}
