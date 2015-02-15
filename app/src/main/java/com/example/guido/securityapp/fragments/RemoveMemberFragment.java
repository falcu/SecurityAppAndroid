package com.example.guido.securityapp.fragments;

import com.example.guido.securityapp.asyncTasks.OperationMemberTask;
import com.example.guido.securityapp.asyncTasks.RemoveMemberTask;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * Created by guido on 2/11/15.
 */
public class RemoveMemberFragment extends OperationMemberFragment{
    @Override
    protected OperationMemberTask makeTask(NewMemberTO newMemberTO) {
        return new RemoveMemberTask(newMemberTO);
    }

    @Override
    protected String getActionDescription() {
        return "REMOVE MEMBER";
    }

    @Override
    protected void postSuccessMessage() {
        new ToastHelper().showLongDurationMessage(getActivity(),"User was removed from the group");

    }
}
