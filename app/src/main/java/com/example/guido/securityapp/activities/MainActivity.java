package com.example.guido.securityapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.BuilderRegisterIdService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.services.ServiceRegisterId;


public class MainActivity extends ActionBarActivity {

    private ServiceRegisterId serviceRegisterId;
    private ISignService signService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO REMOVE DUMMY CODE
        Intent createGroupIntent = new Intent(this,CreateGroupActivity.class);
        startActivity(createGroupIntent);
        //END TODO

   /*     setRegisterIdService();
        setSignService();
        try {
            serviceRegisterId.setRegistrationIdWithErrorDialogAsync(this);
            if(!signService.isUserSingedIn())
            {
                Intent intent = new Intent(this,SignInActivity.class);
                this.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    protected void setRegisterIdService()
    {
        serviceRegisterId = BuilderRegisterIdService.buildRegisterIdService();
    }

    protected void setSignService()
    {
        signService = BuilderSignService.buildServiceSign(BuilderSignService.SignOptions.SIGN_IN);
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
