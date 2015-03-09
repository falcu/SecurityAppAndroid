package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.GetGroupInfoTask;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.interfaces.ITaskHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class SynchronizeExistentGroupFragment extends BaseFragmentOption implements View.OnClickListener{

    private Button synchronizeAction;


    public SynchronizeExistentGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View theView = inflater.inflate(R.layout.fragment_synchronize_existent_group, container, false);
        synchronizeAction = (Button) theView.findViewById(R.id.synchronize_group_action);
        synchronizeAction.setOnClickListener(this);
        theView.setVisibility(View.GONE);
        return theView;
    }


    @Override
    public void onClick(View v) {
        AsynTaskWithHandlers task = new GetGroupInfoTask();
        task.addHandler((ITaskHandler)getActivity());
        task.execute((Void)null);
    }
}
