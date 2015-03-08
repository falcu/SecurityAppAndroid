package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.fragments.DescriptionFragment;
import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.Group;

public class CreateGroupActivity extends Activity implements IFragmentResultHandler,IFragmentExceptionHandler, ITaskHandler, ISubscriber {

    private IProgressBar progressBar;
    private IEventAggregator eventAggregator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(findViewById(R.id.group_form));
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.Subscribe(this,MyApplication.getContext().getResources().getString(R.string.group_updated_event));
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
        hideFragment(R.id.create_group_fragment);
        hideFragment(R.id.description_fragment);
        ((DescriptionFragment)getFragmentManager().findFragmentById(R.id.description_fragment)).setDescription("In order to join a group, the creator must add you to the group. The creator just needs your email");
        IFragmentOptions fragmentOptions = (IFragmentOptions) getFragmentManager().findFragmentById(R.id.options_fragment);
        fragmentOptions.addOption(new Option(MyApplication.getContext().getString(R.string.create_group_text),MyApplication.getContext().getString(R.string.create_group_key)));
        fragmentOptions.addOption(new Option(MyApplication.getContext().getString(R.string.join_group_text),MyApplication.getContext().getString(R.string.join_group_key)));
    }


    @Override
    public void handle(Object data) {
        String key = (String)data;
        if(key.equals(getString(R.string.create_group_key)))
        {
            showFragment(R.id.create_group_fragment);
            hideFragment(R.id.description_fragment);
        }
        else if(key.equals(getString(R.string.join_group_key)))
        {
            hideFragment(R.id.create_group_fragment);
            showFragment(R.id.description_fragment);
        }

    }

    private void showFragment(int id)
    {
        ((IFragmentVisibility) getFragmentManager().findFragmentById(id)).show();
    }

    private void hideFragment(int id)
    {
        ((IFragmentVisibility) getFragmentManager().findFragmentById(id)).hide();
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
        } else {
            showError(taskResult.getError().getErrorMessage());
        }
    }

    protected void showError(String error)
    {
        new AlertDialog.Builder(this).setTitle("Error").setMessage(error).show();
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
