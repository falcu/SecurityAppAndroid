package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.CreateGroupTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.services.ServiceUserToken;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment implements IFragmentVisibility, View.OnClickListener, ITaskHandler {

    protected EditText groupNameView;
    protected IValidate groupNameValidator;
    protected ServiceUserToken serviceUserToken;

    public CreateGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_create_group, container, false);
        groupNameView = (EditText) theView.findViewById(R.id.group_name);
        BuilderValidator builderValidator = new BuilderValidator();
        groupNameValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.GROUP_NAME);
        serviceUserToken = BuilderServiceUserToken.build();

        Button button = (Button) theView.findViewById(R.id.create_group_action);
        button.setOnClickListener(this);
        return theView;
    }


    @Override
    public void show() {
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        getView().setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(!checkAndSetError())
        {
            try
            {
                AsynTaskWithHandlers task = createTask(getName());
                task.execute((Void)null);
            }
            catch (UnableToLoadTokenException e){
                IFragmentExceptionHandler exceptionHandler = (IFragmentExceptionHandler) getActivity();
                exceptionHandler.handle(e);
            }
            catch (Exception e){
                //TODO HANDLE MORE GENERAL EXCEPTION
            }

        }
    }

    public boolean checkAndSetError()
    {
        groupNameView.setError(null);
        String groupName = getName();
        String error = groupNameValidator.getError(groupName);
        if(!error.isEmpty())
        {
            groupNameView.setError(error);
            groupNameView.requestFocus();
            return true;
        }
        return false;
    }

    public String getName()
    {
        return groupNameView.getText().toString();
    }

    protected AsynTaskWithHandlers createTask(String groupName) throws Exception
    {
            String token = serviceUserToken.getToken();
            Object transferObject = makeTransferObject(token,groupName);
            CreateGroupTask task = new CreateGroupTask(transferObject);
            task.addHandler(this);
            task.addHandler((ITaskHandler)getActivity());
            return task;
    }

    protected Object makeTransferObject(String token,String groupName)
    {
        CreateGroupTO transferObject = new CreateGroupTO();
        transferObject.setName(groupName);
        transferObject.setToken(token);
        return transferObject;
    }

    @Override
    public void onPreExecute(String identifier) {

    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        if(taskResult.isSuccesful())
        {
            ToastHelper toastHelper = new ToastHelper();
            toastHelper.showLongDurationMessage(MyApplication.getContext(),(String)taskResult.getResult());
        }
        else
        {
            groupNameView.setError(taskResult.getError());
            groupNameView.requestFocus();
        }
    }

    @Override
    public void onCancelled(String identifier) {

    }
}
