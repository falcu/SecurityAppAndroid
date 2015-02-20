package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.services.BuilderServiceDataCorrupted;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.factories.FactoryGroupConfigurationFragments;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.services.ServiceDataCorrupted;

import java.util.Iterator;

public class GroupConfigurationCreatorActivity extends Activity implements IFragmentResultHandler,ITaskHandler,IFragmentExceptionHandler {

    private IProgressBar progressBar;
    private FactoryGroupConfigurationFragments factoryFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_configuration);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(findViewById(R.id.group_configuration_form));
        factoryFragments = new FactoryGroupConfigurationFragments(this);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceDataCorrupted service = BuilderServiceDataCorrupted.build();
        if(service.isDataCorrupted())
        {
            Intent i = makeForceFinishActivityIntent();
            finishActivityWith(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initialize()
    {
        try
        {
            IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
                Iterator<BaseFragmentOption> fragments = getFragments();
                while(fragments.hasNext())
                {
                    BaseFragmentOption fragment = fragments.next();
                    fragmentOptions.addOption(new Option(fragment.getDescription(),fragment.getKey()));
                }
        }
        catch (Exception e)
        {

        }
    }

    protected Iterator<BaseFragmentOption> getFragments()
    {
        try
        {
            return factoryFragments.getFragments();
        }
        catch (Exception e)
        {
            //TODO HANDLE EXCEPTION
            return null;
        }
    }
    protected BaseFragmentOption getFragmentByKey(String key) {
        try
        {
            return factoryFragments.getFragmentByKey(key);
        }
        catch (Exception e)
        {
            //TODO HANDLE EXCEPTION
            return null;
        }
    }


    @Override
    public void handle(Object data) {
        String key = (String) data;
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
    public void onPreExecute() {
        progressBar.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        progressBar.showProgress(false);
        if(taskResult.isSuccesful())
        {
            String taskIdentifier = taskResult.getIdentifier();
            if(taskIdentifier.equals(MyApplication.getContext().getResources().getString(R.string.quit_group_action_key)))
            {
                Intent i = makeForceFinishActivityIntent();
                new ToastHelper().showLongDurationMessage(this,"You've quit the group!");
                finishActivityWith(i);
            }
            else

            {
                Intent i = new Intent();
                finishActivityWith(i);
            }
        }
    }

    @Override
    public void onCancelled() {
        progressBar.showProgress(false);
    }

    @Override
    public void handle(Exception e) {
        if(e.getClass().equals(UnableToLoadGroupException.class) || e.getClass().equals(UnableToLoadTokenException.class))
        {
            Intent i = makeForceFinishActivityIntent();
            finishActivityWith(i);
        }
    }

    public void finishActivityWith(Intent intent)
    {
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    protected Intent makeForceFinishActivityIntent()
    {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.FORCE_FINISH_ACTIVITY),true);
        return i;
    }







}
