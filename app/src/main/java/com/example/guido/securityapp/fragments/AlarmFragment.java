package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.ICommand;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment implements View.OnTouchListener {

    private Button button;
    private final long pressedDuration = 3000; //in milliseconds
    private Thread longClickSensor = null;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_alarm, container, false);
        button = (Button) theView.findViewById(R.id.alarm_action);
        button.setOnTouchListener(this);

        return theView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && longClickSensor == null) {
            longClickSensor = new Thread(new DelayedAction(pressedDuration,new DummyCommand()));
            longClickSensor.start();
        } else if (event.getAction() == MotionEvent.ACTION_UP && longClickSensor!=null) {
            longClickSensor.interrupt();
            longClickSensor = null;
        }
        return false;
    }

    private class DelayedAction implements Runnable
    {
       private long timeToDelayAction; //in milliseconds
       private ICommand command;
        public DelayedAction(long timeToDelayAction, ICommand command)
        {
            this.timeToDelayAction = timeToDelayAction;
            this.command = command;
        }
        @Override
        public void run() {
            try
            {
                Thread.sleep(timeToDelayAction);
                command.execute();
            }
            catch (Exception e){return;}
        }
    }

    private class DummyCommand implements ICommand
    {

        @Override
        public void execute() {

        }
    }
}
