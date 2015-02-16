package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.RenameGroupTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.RenameGroupTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenameGroupFragment extends BaseFragmentOption implements View.OnClickListener, ITaskHandler {

    protected CreateGroupFragment helperFragment;


    public RenameGroupFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helperFragment = new CreateGroupFragment();
        View theView = helperFragment.onCreateView(inflater,container,savedInstanceState);
        Button action = (Button) theView.findViewById(R.id.create_group_action);
        action.setOnClickListener(this);
        action.setText("RENAME GROUP");
        theView.setVisibility(View.GONE);
        return theView;
    }


    @Override
    public void onClick(View v) {
        if(!helperFragment.checkAndSetError())
        {
            try
            {
                String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();
                String token = BuilderServiceUserToken.build().getToken();
                String name = helperFragment.getName();
                RenameGroupTO renameGroupTO = new RenameGroupTO(name,groupId,token);
                AsynTaskWithHandlers task = new RenameGroupTask(renameGroupTO);
                task.addHandler(this);
                task.addHandler((ITaskHandler)getActivity());
                task.execute((Void)null);
            }
            catch (UnableToLoadGroupException|UnableToLoadTokenException e)
            {
                //TODO HANDLE ERROR
                IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
                handler.handle(e);
            }
            catch (Exception e)
            {
                //TODO HANDLE GENERALE EXCEPTION
            }

        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        helperFragment.onPostExecute(taskResult);
    }

    @Override
    public void onCancelled() {

    }
}
