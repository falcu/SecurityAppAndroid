package com.example.guido.securityapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.commands.Command;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelayedActionFragmentWithProgressBar extends DelayedActionFragment{

    protected ProgressBar progressBarTimer;
    protected ProgressTimer timer = null;

    public DelayedActionFragmentWithProgressBar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_delayed_action_fragment_with_progress_bar, container, false);
        button = (ImageButton) theView.findViewById(R.id.alarm_action);
        button.setOnTouchListener(this);
        progressBarTimer = (ProgressBar)theView.findViewById(R.id.progress_bar_timer);
        progressBarTimer.setVisibility(View.INVISIBLE);
        return theView;
    }

    @Override
    protected void actionDown()
    {
        timer = new ProgressTimer(progressBarTimer,3000,action);
        timer.start();
    }

    @Override
    protected void actionUp()
    {
        timer.cancel();
        timer = null;
    }

    public class ProgressTimer
    {
        private ProgressBar progressBar;
        private long timeInMilliseconds;
        private Command actionToPerform;
        CountDownTimer timer;
        private final int totalTicks = 21; //the last tick is not ejecuted by design
        private int i;

        public ProgressTimer(ProgressBar progressBar, long milliseconds, Command action)
        {
            this.progressBar = progressBar;
            this.timeInMilliseconds = milliseconds;
            this.actionToPerform = action;
            i = 0;
            timer = null;
        }

        public void start()
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(totalTicks-1);
            long countDownInterval = timeInMilliseconds/totalTicks;
            timer = new CountDownTimer(timeInMilliseconds,countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    i++;
                    progressBar.setProgress(i);
                }

                @Override
                public void onFinish() {
                    i++;
                    progressBar.setProgress(i);
                    actionToPerform.execute();
                }
            }.start();
        }

        public void cancel()
        {
            timer.cancel();
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
