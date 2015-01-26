package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupOptionsFragment extends Fragment implements View.OnClickListener{


    public GroupOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView =  inflater.inflate(R.layout.fragment_group_options, container, false);
        setupClickListener(theView,R.id.create_group_option);
        setupClickListener(theView,R.id.join_group_option);
        return theView;
    }

    private void setupClickListener(View theView, int childViewId) {
        View childView = theView.findViewById(childViewId);
        childView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        String key = translateIdToKey(id);
        IFragmentResultHandler handler = (IFragmentResultHandler) getActivity();
        handler.handle(key);
    }

    private String translateIdToKey(int id)
    {
        switch (id)
        {
            case R.id.create_group_option:
                return getString(R.string.create_group_key);
            case R.id.join_group_option:
                return getString(R.string.join_group_key);
        }
        return null;
    }
}
