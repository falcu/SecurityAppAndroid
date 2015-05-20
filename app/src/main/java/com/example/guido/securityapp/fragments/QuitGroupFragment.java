package com.example.guido.securityapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.asyncTasks.QuitGroupTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.helpers.ConfirmDialogHelper;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.OnYesClickListener;
import com.example.guido.securityapp.models.QuitGroupTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuitGroupFragment extends BaseFragmentOption implements View.OnClickListener, OnYesClickListener, ITaskHandler {

    protected Button quitAction;
    protected ConfirmDialogHelper dialogHelper;


    public QuitGroupFragment() {
        dialogHelper = new ConfirmDialogHelper();
        dialogHelper.setOnYesClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_quit_group, container, false);
        quitAction = (Button) theView.findViewById(R.id.quit_group_action);
        quitAction.setOnClickListener(this);
        theView.setVisibility(View.GONE);
        return theView;
    }


    @Override
    public void onClick(View v) {
        dialogHelper.showYesNoDialog(getActivity(), "Quit Group", "Are you sure you want to quit?");
    }

    @Override
    public void onYesClicked() {
        try
        {
            String token = BuilderServiceUserToken.build().getToken();
            String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();
            QuitGroupTO quitGroupTO = new QuitGroupTO(groupId,token);
            QuitGroupTask task = new QuitGroupTask(quitGroupTO);
            task.addHandler(this);
            task.addHandler((ITaskHandler)getActivity());
            task.setResultIdentifier(MyApplication.getContext().getString(R.string.quit_group_action_key));
            task.execute((Void)null);
        }
        catch (UnableToLoadTokenException|UnableToLoadGroupException e)
        {

        }
        catch (Exception e)
        {
            //TODO HANDLE MORE GENERAL EXCETION
        }

    }

    @Override
    public void onPreExecute(String identifier) {

    }

    @Override
    public void onPostExecute(TaskResult taskResult) {

    }

    @Override
    public void onCancelled(String identifier) {

    }
}
