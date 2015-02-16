package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.factories.FactoryGroupConfigurationFragments;
import com.example.guido.securityapp.fragments.BaseFragmentOption;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;

import java.util.Iterator;

public class GroupConfigurationCreatorActivity extends Activity implements IFragmentResultHandler,ITaskHandler,IFragmentExceptionHandler {

    private IProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_configuration);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(findViewById(R.id.group_configuration_form));
        initialize();
    }

    private void initialize()
    {
        try
        {
            IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
                Iterator<BaseFragmentOption> fragments = getFragments();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                while(fragments.hasNext())
                {
                    BaseFragmentOption fragment = fragments.next();
                    fragmentTransaction.add(R.id.group_configuration_options,fragment);
                    fragmentOptions.addOption(new Option(fragment.getDescription(),fragment.getKey()));
                }
                fragmentTransaction.commit();
        }
        catch (Exception e)
        {

        }
    }

    protected Iterator<BaseFragmentOption> getFragments()
    {
        return FactoryGroupConfigurationFragments.getCreatorFragments();
    }
    protected BaseFragmentOption getFragmentByKey(String key) {
        return FactoryGroupConfigurationFragments.getFragmentByKey(key);
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
            finish();
        }
    }

    @Override
    public void onCancelled() {
        progressBar.showProgress(false);
    }

    @Override
    public void handle(Exception e) {

    }
}
