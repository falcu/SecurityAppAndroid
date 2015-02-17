package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.PanicNotificationTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.adapters.BuilderGroupMembersAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.builders.services.BuilderServiceSignOut;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.NoAvailableLocation;
import com.example.guido.securityapp.helpers.ConfirmDialogHelper;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.ICommand;
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;
import com.example.guido.securityapp.interfaces.IListFragment;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IUpdate;
import com.example.guido.securityapp.interfaces.OnYesClickListener;
import com.example.guido.securityapp.models.MyLocation;
import com.example.guido.securityapp.models.PanicTO;
import com.example.guido.securityapp.services.ServiceLocation;
import com.example.guido.securityapp.services.ServiceSignOut;

public class SecurityActivity extends ActionBarActivity implements ITaskHandler {

    private IProgressBar progressBar;
    private ConfirmDialogHelper signOutConfigmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        initializeFragments();
        signOutConfigmDialog = new ConfirmDialogHelper();
        signOutConfigmDialog.setOnYesClickListener(new OnYesClickListener() {
            @Override
            public void onYesClicked() {
                ServiceSignOut serviceSignOut = BuilderServiceSignOut.build();
                serviceSignOut.signOut();
                if(!serviceSignOut.wasRequestWithError())
                {
                    ToastHelper toastHelper= new ToastHelper();
                    toastHelper.showLongDurationMessage(SecurityActivity.this,"You signed out!");
                    SecurityActivity.this.finishActivity();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.IS_ACTIVITY_FINISH),false)) {
                    finishActivity();
                }
                else if(data.getBooleanExtra(MyApplication.getContext().getResources().getString(R.string.UPDATE_GROUP),false))
                {
                    IUpdate listFragment = (IUpdate) getFragmentManager().findFragmentById(R.id.members_list_fragment);
                    IUpdate groupDetails = (IUpdate) getFragmentManager().findFragmentById(R.id.group_details_fragment);
                    listFragment.update();
                    groupDetails.update();
                }
            }
        }
    }

    protected void finishActivity()
    {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.IS_ACTIVITY_FINISH),true);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreExecute() {
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
            toast.showLongDurationMessage(this,taskResult.getError());
        }
    }

    @Override
    public void onCancelled() {
        progressBar.showProgress(false);
    }

    public class PanicNotificationCommand implements ICommand
    {

        private String message = "I need help";
        private ITaskHandler handler;

        public PanicNotificationCommand(ITaskHandler handler){this.handler = handler;}
        public PanicNotificationCommand(ITaskHandler handler,String message){
            this(handler);
            this.message = message;}

        @Override
        public void execute() {
            try
            {
                PanicTO panicTO = makePanicTO();
                PanicNotificationTask task = new PanicNotificationTask(panicTO);
                task.addHandler(handler);
                task.execute((Void)null);

            }
            catch (Exception e){
                //TODO HANDLE
            }
        }

        private PanicTO makePanicTO() throws Exception{
            PanicTO panicTO = new PanicTO();
            String token = BuilderServiceUserToken.build().getToken();
            String groupId = BuilderServiceGroup.buildGroupInformationService().getGroup().getId();
            MyLocation location = BuilderServiceLocationSingleton.getServiceLocation().getLocation();
            panicTO.setToken(token);
            panicTO.setGroupId(groupId);
            panicTO.setMessage(message);
            panicTO.setLocation(location);

            return panicTO;

        }
    }

    private class DummyCommand implements ICommand
    {

        @Override
        public void execute() {
            ServiceLocation serviceLocation = BuilderServiceLocationSingleton.getServiceLocation();
            try
            {
                MyLocation myLocation = serviceLocation.getLocation();
                String message = "Latitude: " +myLocation.getLatitude()+"\n"+"Longitude: "+myLocation.getLongitude()+"\n"+"Old: "+myLocation.getTimeOld();
            }
            catch (NoAvailableLocation e)
            {
            }
        }
    }


}
