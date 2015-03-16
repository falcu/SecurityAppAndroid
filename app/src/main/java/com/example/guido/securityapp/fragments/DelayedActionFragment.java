package com.example.guido.securityapp.fragments;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.asyncTasks.DelayedAction;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.interfaces.IFragmentDelayedButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelayedActionFragment extends Fragment implements View.OnTouchListener, IFragmentDelayedButton {

    protected ImageButton button;
    protected long pressedDuration = 3000; //in milliseconds
    protected Command action;
    private Thread longClickSensor = null;

    public DelayedActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_delayed_action, container, false);
        button = (ImageButton) theView.findViewById(R.id.alarm_action);
        button.setOnTouchListener(this);

        return theView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            actionDown();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            actionUp();
        }
        return false;
    }

    protected void actionDown()
    {
        if(longClickSensor == null) {
            longClickSensor = new Thread(new DelayedAction(pressedDuration, action));
            longClickSensor.start();
        }
    }

    protected void actionUp()
    {
        if(longClickSensor != null) {
            longClickSensor.interrupt();
            longClickSensor = null;
        }
    }


    @Override
    public void setDelayedAction(Command action) {
        this.action = action;
    }

    @Override
    public void setImage(int resourceId) {
        Drawable drawable = MyApplication.getContext().getResources().getDrawable(resourceId);
        button.setBackground(drawable);
    }

    @Override
    public void setDelayTime(int milliseconds) {
        this.pressedDuration = milliseconds;
    }


}
