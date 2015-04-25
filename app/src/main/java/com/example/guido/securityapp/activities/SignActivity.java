package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.exceptions.ExceptionHandler;
import com.example.guido.securityapp.exceptions.ExceptionHandlerWithDialog;
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
    @Override
    public void onPreExecute(String identifier)
    {
        progressBar.showProgress(true);
    }

    protected void beforeFinish() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.FORCE_FINISH_ACTIVITY),true);
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
            if(taskResult.getError().getException()!=null)
            {
                ExceptionHandler exceptionHandler = new ExceptionHandlerWithDialog(this);
                exceptionHandler.handleException(taskResult.getError().getException());
            }
            else{

                getFragmentErrorSetter().setError(getString(R.string.password_error_key),taskResult.getError().getErrorMessage());
            }
        }
    }
    @Override
    public void onCancelled(String identifier) {
        authTask = null;
        progressBar.showProgress(false);
    }
}
