package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AddMemberTask;
import com.example.guido.securityapp.asyncTasks.OperationMemberTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderServiceUserToken;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.helpers.ToastWrapper;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceGroupInformation;

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
