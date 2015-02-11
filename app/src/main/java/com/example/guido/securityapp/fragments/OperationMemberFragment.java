package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IValidate;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class OperationMemberFragment extends BaseFragmentOption implements View.OnClickListener, ITaskHandler{


    protected EditText userEmailEditText;
    protected IValidate emailValidator;
    protected Button actionButton;
    protected int id;
    protected String key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_operation_member, container, false);
        userEmailEditText = (EditText) theView.findViewById(R.id.member_email);
        emailValidator = makeBuilderValidator().buildValidator(BuilderValidator.ValidatorType.EMAIL);
        actionButton = (Button) theView.findViewById(R.id.operation_member_action);
        actionButton.setOnClickListener(this);
        actionButton.setText(getActionDescription());
        theView.setVisibility(View.GONE);
        return theView;
    }

    protected BuilderValidator makeBuilderValidator()
    {
        return new BuilderValidator();
    }


    @Override
    public void onClick(View v) {
        userEmailEditText.setError(null);
        String email = userEmailEditText.getText().toString();
        String error = emailValidator.getError(email);
        if(error.isEmpty())
        {
            try
            {
                actionToPerform();
            }
            catch (Exception e){
                userEmailEditText.setError(e.getMessage());
                userEmailEditText.requestFocus();
            }

        }
        else
        {
            userEmailEditText.setError(error);
            userEmailEditText.requestFocus();
        }

    }

    protected abstract void actionToPerform();

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        if(!taskResult.isSuccesful())
        {
            userEmailEditText.setError(taskResult.getError());
            userEmailEditText.requestFocus();
        }
        else
        {
            postSuccessMessage();
        }
    }

    @Override
    public void onCancelled() {

    }

    protected abstract String getActionDescription();

    protected abstract void postSuccessMessage();



}
