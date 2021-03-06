package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.adapters.BuilderGroupMembersAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceDeleteAll;
import com.example.guido.securityapp.commands.PanicNotificationCommand;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.helpers.ConfirmDialogHelper;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;
import com.example.guido.securityapp.interfaces.IListFragment;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ISubscriber;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.OnYesClickListener;
import com.example.guido.securityapp.models.GroupDeleted;
import com.example.guido.securityapp.services.ServiceDeleteAll;

public class SecurityActivity extends ActionBarActivity implements ITaskHandler, ISubscriber {

    private IProgressBar progressBar;
    private ConfirmDialogHelper signOutConfigmDialog;
    private IEventAggregator eventAggregator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        initializeFragments();
        signOutConfigmDialog = new ConfirmDialogHelper();
        signOutConfigmDialog.setOnYesClickListener(new OnYesClickListener() {
            @Override
            public void onYesClicked() {
                ServiceDeleteAll serviceSignOut = BuilderServiceDeleteAll.buildSignOut();
                serviceSignOut.deleteAll();
                if(!serviceSignOut.wasRequestWithError())
                {
                    ToastHelper toastHelper= new ToastHelper();
                    toastHelper.showLongDurationMessage(SecurityActivity.this,"You signed out!");
                    SecurityActivity.this.finishActivity();
                }
            }
        });
        eventAggregator = FactoryEventAggregator.getInstance();
        eventAggregator.subscribe(this, MyApplication.getContext().getResources().getString(R.string.DELETED_FROM_GROUP));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.BACK_BUTTON_PRESSED),true);
        setResult(Activity.RESULT_OK,i);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.FORCE_FINISH_ACTIVITY),false)) {
                    finishActivity();
                }
                else if(data.getBooleanExtra(MyApplication.getContext().getResources().getString(R.string.QUIT_GROUP),false))
                {
                    finishActivity();
                    BuilderServiceDeleteAll.buildQuitGroup().deleteAll();
                }
            }
        }
    }

    protected void finishActivity()
    {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.FORCE_FINISH_ACTIVITY),true);
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    private void initializeFragments()
    {
        IFragmentDelayedButton delayedAction = (IFragmentDelayedButton) getFragmentManager().findFragmentById(R.id.alarm_fragment);
        delayedAction.setImage(R.drawable.panic_icon);
        delayedAction.setDelayedAction(new PanicNotificationCommand(this));
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.alarm_progress_bar);
        progressBar.showProgress(false);
        IListFragment listFragment = (IListFragment) getFragmentManager().findFragmentById(R.id.members_list_fragment);
        listFragment.setBuilderAdapter(new BuilderGroupMembersAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_security, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_group_configuration) {
            Intent i = new Intent(this,GroupConfigurationCreatorActivity.class);
            startActivityForResult(i, MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
        }
        else if(id == R.id.action_sign_out)
        {
            signOutConfigmDialog.showYesNoDialog(this,"SIGN OUT","Are you sure you want to sign out?");
        }
        else if(id == R.id.action_alarms_history)
        {
            Intent i = new Intent(this,AlarmsHistoryActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_panic_message)
        {
            Intent i = new Intent(this,PanicMessageConfigurationActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_localities_customization)
        {
            Intent i = new Intent(this,LocalitiesCustomizationActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_localities_notification_history)
        {
            Intent i = new Intent(this,LocalitiesNotificationHistoryActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreExecute(String identifier) {
        progressBar.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        progressBar.showProgress(false);
        ToastHelper toast = new ToastHelper();
        if(taskResult.isSuccesful())
        {
            toast.showLongDurationMessage(this,(String)taskResult.getResult());
        }
        else
        {
            toast.showLongDurationMessage(this,taskResult.getError().getErrorMessage());
        }
    }

    @Override
    public void onCancelled(String identifier) {
        progressBar.showProgress(false);
    }

    @Override
    public void onEvent(Object data) {
        if(data instanceof GroupDeleted)
        {
            finishActivity();
        }
    }
}
