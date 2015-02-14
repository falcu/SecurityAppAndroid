package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.services.BuilderServiceLocationSingleton;
import com.example.guido.securityapp.exceptions.NoAvailableLocation;
import com.example.guido.securityapp.interfaces.ICommand;
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;
import com.example.guido.securityapp.models.MyLocation;
import com.example.guido.securityapp.services.ServiceLocation;

public class SecurityActivity extends Activity {
    private TextView dummyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        dummyTextView = (TextView)findViewById(R.id.dummyTextView);
        initializeFragments();
    }

    private void initializeFragments()
    {
        IFragmentDelayedButton delayedAction = (IFragmentDelayedButton) getFragmentManager().findFragmentById(R.id.alarm_fragment);
        delayedAction.setImage(R.drawable.panic_icon);
        delayedAction.setDelayedAction(new DummyCommand());
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
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
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
                dummyTextView.setText(message);
            }
            catch (NoAvailableLocation e)
            {
                dummyTextView.setText("No available location");
            }
        }
    }


}
