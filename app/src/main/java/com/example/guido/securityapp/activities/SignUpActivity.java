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
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public class SignUpActivity extends SignActivity {

    private EditText nameView;
    private IValidate nameValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameView = (EditText) findViewById(R.id.name);
        BuilderValidator builderValidator = new BuilderValidator();
        nameValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.NAME);

    }

    @Override
    protected void setCurrentContextView() {
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected AsynTaskWithHandlers getSignTask() {
        String email = getFragmentData().getData().get(getString(R.string.email_key)).toString();
        String password = getFragmentData().getData().get(getString(R.string.password_key)).toString();
        String name = nameView.getText().toString();
        UserSignUpTask task = new UserSignUpTask(name, email, password,this);
        task.addHandler(this);
        return task;
    }

    @Override
    protected int getFragmentId() {
        return R.id.signup_fragment;
    }

    @Override
    protected boolean validateFields() {
        return validateName() && validateEmail() && validatePassword();
    }

    private boolean validateName()
    {
        nameView.setError(null);
        String name = nameView.getText().toString();
        String nameError = nameValidator.getError(name);
        if(!nameError.isEmpty())
        {
            nameView.setError(nameError);
            nameView.requestFocus();
            return false;
        }
        return true;
    }

}
