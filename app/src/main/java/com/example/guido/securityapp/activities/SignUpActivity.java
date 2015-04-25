package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.asyncTasks.UserSignInTask;
import com.example.guido.securityapp.asyncTasks.UserSignUpTask;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.interfaces.IValidateFragment;
import com.example.guido.securityapp.models.UserTO;

/**
 * Created by guido on 1/24/15.
 */
public class SignUpActivity extends SignActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCurrentContextView() {
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected AsynTaskWithHandlers getSignTask() {
        String email = getFragmentData().getData().get(getString(R.string.email_key)).toString();
        String password = getFragmentData().getData().get(getString(R.string.password_key)).toString();
        String passwordConfirmation = getFragmentData().getData().get(getString(R.string.reentry_password_key)).toString();
        String name = getFragmentData().getData().get(getString(R.string.name_key)).toString();
        try
        {
            UserTO userTO = new UserTO();
            userTO.setName(name);
            userTO.setEmail(email);
            userTO.setPassword(password);
            userTO.setPasswordConfirmation(passwordConfirmation);
            UserSignUpTask task = new UserSignUpTask(userTO,this);
            task.addHandler(this);
            return task;
        }
        catch (Exception e)
        {
            return null;
            //TODO handle error
        }
    }

    @Override
    protected int getFragmentId() {
        return R.id.signup_fragment;
    }

    @Override
    protected boolean validateFields() {
        IValidateFragment fragment = (IValidateFragment) getFragmentManager().findFragmentById(getFragmentId());
        return fragment.validateFragment();
    }

    @Override
    protected void beforeFinish() {
        super.beforeFinish();
        ToastHelper helper = new ToastHelper();
        helper.showLongDurationMessage(this,"You had successfully created a new user!");
    }

}
