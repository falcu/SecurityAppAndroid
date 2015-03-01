package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.helpers.ProgressBarHelper;
import com.example.guido.securityapp.interfaces.IProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressBarFragment extends Fragment implements IProgressBar{


    private View progressView;
    private ProgressBarHelper helperProgressBar;


    public ProgressBarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        progressView = view.findViewById(R.id.login_progress);
        helperProgressBar = new ProgressBarHelper(progressView);
        helperProgressBar.setAnimationTime(MyApplication.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        return view;
    }

    @Override
    public void setControllableView(View view) {
        helperProgressBar.setControllableView(view);
    }

    @Override
    public void showProgress(final boolean show) {
        helperProgressBar.showProgress(show);
    }

    @Override
    public void collapseWhenNotShown(boolean collapseWhenNotShown) {
        helperProgressBar.collapseWhenNotShown(collapseWhenNotShown);
    }

}
