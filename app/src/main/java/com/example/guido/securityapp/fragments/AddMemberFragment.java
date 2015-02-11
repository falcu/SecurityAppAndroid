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
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderServiceUserToken;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceGroupInformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends BaseFragmentOption implements View.OnClickListener, ITaskHandler{

    protected EditText userEmailEditText;
    protected IValidate emailValidator;
    protected Button addMemberButton;
    protected int id;
    protected String key;

    public AddMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_operation_member, container, false);
        userEmailEditText = (EditText) theView.findViewById(R.id.member_email);
        emailValidator = makeBuilderValidator().buildValidator(BuilderValidator.ValidatorType.EMAIL);
        addMemberButton = (Button) theView.findViewById(R.id.add_member_action);
        addMemberButton.setOnClickListener(this);
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
                ServiceGroupInformation serviceGroupInformation = BuilderGroupService.buildGroupInformationService();
                String groupId = serviceGroupInformation.getGroup().getId();
                String token = BuilderServiceUserToken.build().getToken();
                AddMemberTask addMemberTask = new AddMemberTask(new NewMemberTO(email,groupId,token));
                addMemberTask.addHandler(this);
                addMemberTask.addHandler((ITaskHandler)getActivity());
                addMemberTask.execute((Void)null);
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

    protected void postSuccessMessage()
    {
        showMessage("new member added");
    }

    protected void showMessage(String message)
    {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }
}
