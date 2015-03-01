package com.example.guido.securityapp.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import com.example.guido.securityapp.interfaces.IProgressBar;

/**
 * Created by guido on 2/28/15.
 */
public class ProgressBarHelper implements IProgressBar {

    private View controllableView = null;
    private View progressView;
    private int animationTime=200; //default;

    public ProgressBarHelper(View progressView) {
        this.progressView = progressView;
    }

    @Override
    public void setControllableView(View view) {
        controllableView = view;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {


            if(getControllableView() !=null)
            {
                getControllableView().setVisibility(show ? View.GONE : View.VISIBLE);
                getControllableView().animate().setDuration(getAnimationTime()).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getControllableView().setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

            }

            getProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
            getProgressView().animate().setDuration(getAnimationTime()).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    getProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            getProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
            if(getControllableView() !=null)
                getControllableView().setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public View getControllableView() {
        return controllableView;
    }

    public View getProgressView() {
        return progressView;
    }

    public void setProgressView(View progressView) {
        this.progressView = progressView;
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }
}
