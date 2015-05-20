package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.exceptions.ExceptionHandler;
import com.example.guido.securityapp.exceptions.ExceptionHandlerWithDialog;
import com.example.guido.securityapp.factories.FactoryCreateGroupFragments;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.factories.FactoryFragmentsOptions;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.Group;

import java.util.Iterator;

public class CreateGroupActivity extends Activity implements IFragmentResultHandler,IFragmentExceptionHandler, ITaskHandler, ISubscriber {

    private IProgressBar progressBar;
    private IEventAggregator eventAggregator;
    private FactoryFragmentsOptions factoryFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(findViewById(R.id.group_form));
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.subscribe(this, MyApplication.getContext().getResources().getString(R.string.UPDATE_GROUP));
        factoryFragments = new FactoryCreateGroupFragments(this);
        initialize();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.BACK_BUTTON_PRESSED),true);
        setResult(Activity.RESULT_OK,i);
        super.onBackPressed();
    }

    private void initialize()
    {
        Iterator<BaseFragmentOption> fragments = getFragments();
        IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
        while(fragments.hasNext())
        {
            BaseFragmentOption currentFragment = fragments.next();
            fragmentOptions.addOption(new Option(currentFragment.getDescription(),currentFragment.getKey()));
        }
    }

    protected Iterator<BaseFragmentOption> getFragments()
    {
        try {
            return factoryFragments.getFragments();
        } catch (Exception e) {
            return null;
        }
    }

    protected BaseFragmentOption getFragmentByKey(String key)
    {
        try {
            return factoryFragments.getFragmentByKey(key);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void handle(Object data) {
        String key = (String)data;
        setFragmentsVisibility(key);

    }

    private void setFragmentsVisibility(String key)
    {
        BaseFragmentOption selectedFragment = getFragmentByKey(key);
        selectedFragment.show();
        Iterator<BaseFragmentOption> fragments = getFragments();
        while(fragments.hasNext())
        {
            BaseFragmentOption fragmentOption = fragments.next();
            if(fragmentOption.getIdentifier()!=selectedFragment.getIdentifier())
            {
                fragmentOption.hide();
            }
        }
    }

    @Override
    public void onPreExecute(String identifier) {
        progressBar.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        progressBar.showProgress(false);

        if (taskResult.isSuccesful()) {
            finishActivityAndNotifyMainActivity();
        } else if (taskResult.getError().getException() != null) {
            ExceptionHandler exceptionHandler = new ExceptionHandlerWithDialog(this);
            exceptionHandler.handleException(taskResult.getError().getException());
        } else if (taskResult.getError().getErrorMessage() != null) {
            ToastHelper toastHelper = new ToastHelper();
            toastHelper.showLongDurationMessage(this, taskResult.getError().getErrorMessage());
        }
    }

    @Override
    public void onCancelled(String identifier) {
        progressBar.showProgress(false);
    }

    @Override
    public void handle(Exception e) {
        //TODO HANDLE EXCEPTION
    }

    @Override
    public void onEvent(Object data) {
        if(data instanceof Group)
        {
            finishActivityAndNotifyMainActivity();
        }
    }

    private void finishActivityAndNotifyMainActivity()
    {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getString(R.string.FORCE_FINISH_ACTIVITY),true);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
