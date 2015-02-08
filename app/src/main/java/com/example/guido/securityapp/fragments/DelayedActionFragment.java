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
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelayedActionFragment extends Fragment implements View.OnTouchListener, IFragmentDelayedButton {

    private Button button;
    private final long pressedDuration = 3000; //in milliseconds
    private Thread longClickSensor = null;
    private ICommand action;

    public DelayedActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_delayed_action, container, false);
        button = (Button) theView.findViewById(R.id.alarm_action);
        button.setOnTouchListener(this);

        return theView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && longClickSensor == null) {
            longClickSensor = new Thread(new DelayedAction(pressedDuration,action));
            longClickSensor.start();
        } else if (event.getAction() == MotionEvent.ACTION_UP && longClickSensor!=null) {
            longClickSensor.interrupt();
            longClickSensor = null;
        }
        return false;
    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    @Override
    public void setDelayedAction(ICommand action) {
        this.action = action;
    }

    public class DelayedAction implements Runnable
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


}
