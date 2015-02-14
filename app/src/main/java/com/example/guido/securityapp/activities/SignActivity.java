package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.asyncTasks.UserSignUpTask;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.IFragmentGetData;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ISetFragmentError;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public abstract class SignActivity extends Activity implements ITaskHandler {

    protected AsynTaskWithHandlers authTask = null;
    protected IProgressBar progressBar;
    protected IValidate emailValidator;
    protected IValidate passwordValidator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCurrentContextView();


        Button mEmailSignUpButton = (Button) findViewById(R.id.email_sign_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        View mLoginFormView = findViewById(R.id.login_form);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.setControllableView(mLoginFormView);

        BuilderValidator builderValidator = new BuilderValidator();
        emailValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.EMAIL);
        passwordValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.PASSWORD);

    }

    protected abstract void setCurrentContextView();

    public void attemptLogin() {
        if (authTask != null) {
            return;
        }
        getFragmentErrorSetter().clearErrors();
        boolean loggin = validateFields();

        if (loggin) {
            // perform the user login attempt.

            authTask = getSignTask();
            authTask.execute((Void) null);
        }
    }

    protected abstract AsynTaskWithHandlers getSignTask();

    protected IFragmentGetData getFragmentData()
    {
        return (IFragmentGetData)this.getFragmentManager().findFragmentById(getFragmentId());
    }

    protected ISetFragmentError getFragmentErrorSetter()
    {
        return (ISetFragmentError)this.getFragmentManager().findFragmentById(getFragmentId());
    }

    protected abstract int getFragmentId();

    protected abstract boolean validateFields();

    protected boolean validateEmail()
    {
        String email = getFragmentData().getData().get(getString(R.string.email_key)).toString();
        String emailError = emailValidator.getError(email);
        if(!emailError.isEmpty())
        {
            getFragmentErrorSetter().setError(getString(R.string.email_error_key), emailError);
            return false;
        }
        return true;
    }

    protected boolean validatePassword()
    {
        String password = getFragmentData().getData().get(getString(R.string.password_key)).toString();
        String passwordError = passwordValidator.getError(password);

        if(!passwordError.isEmpty())
        {
            getFragmentErrorSetter().setError(getString(R.string.password_error_key), getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }

    @Override
    public void onPreExecute()
    {
        progressBar.showProgress(true);
    }

    protected void beforeFinish() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.IS_ACTIVITY_FINISH),true);
        setResult(Activity.RESULT_OK,i);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        authTask = null;
        progressBar.showProgress(false);

        if (taskResult.isSuccesful()) {
            beforeFinish();
            finish();
        } else {
            getFragmentErrorSetter().setError(getString(R.string.password_error_key),taskResult.getError());
        }
    }
    @Override
    public void onCancelled() {
        authTask = null;
        progressBar.showProgress(false);
    }
}
