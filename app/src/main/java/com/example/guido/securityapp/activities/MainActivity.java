package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.GetGroupInfoTask;
import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderRegisterIdService;
import com.example.guido.securityapp.builders.BuilderServiceUserToken;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.converters.json.HttpCreateGroupResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceStore;


public class MainActivity extends ActionBarActivity {

    private ServiceRegisterId serviceRegisterId;
    private ISignService signService;
    private ActivityCoordinator activityCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        //TODO REMOVE DUMMY CODE

        try
        {
            serviceRegisterId = makeRegisterService();
            serviceRegisterId.setRegistrationIdWithErrorDialogAsync(this);
            activityCoordinator = makeActivityCoordinator();
            activityCoordinator.runCorrespondingActivityFromWithRequestCode(this,MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
        }
        catch (Exception e){}

    }

    protected ActivityCoordinator makeActivityCoordinator()
    {
        return new ActivityCoordinator();
    }

    protected ServiceRegisterId makeRegisterService()
    {
        return BuilderRegisterIdService.buildRegisterIdService();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.IS_ACTIVITY_FINISH),false))
                {
                    try {
                        activityCoordinator.runCorrespondingActivityFromWithRequestCode(this,MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
                    }
                    catch (Exception e)
                    {
                        return;
                    }
                }

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
