package com.example.guido.securityapp.fragments;


import android.app.Fragment;

import com.example.guido.securityapp.asyncTasks.AddMemberTask;
import com.example.guido.securityapp.asyncTasks.OperationMemberTask;
import com.example.guido.securityapp.helpers.ToastWrapper;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends OperationMemberFragment{

    public AddMemberFragment() {
        // Required empty public constructor
    }

    @Override
    protected OperationMemberTask makeTask(NewMemberTO newMemberTO) {
        return new AddMemberTask(newMemberTO);
    }

    @Override
    protected String getActionDescription() {
        return "ADD MEMBER";
    }

    @Override
    protected void postSuccessMessage()
    {
        new ToastWrapper().showLongDurationMessage(getActivity(),"New member added");
    }


}
