package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.UserSignInTask;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IValidateFragment;
import com.example.guido.securityapp.models.UserTO;

public class SignInActivity extends SignActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView signUpTextView = (TextView)findViewById(R.id.signUpLink);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                SignInActivity.this.startActivityForResult(intent,MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(MyApplication.getContext().getResources().getString(R.string.BACK_BUTTON_PRESSED),true);
        setResult(Activity.RESULT_OK,i);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.FORCE_FINISH_ACTIVITY),false)) {
                    beforeFinish();
                    finish();
                }
            }
        }
    }

    @Override
    protected void setCurrentContextView() {
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected AsynTaskWithHandlers getSignTask() {
        String email = getFragmentData().getData().get(getString(R.string.email_key)).toString();
        String password = getFragmentData().getData().get(getString(R.string.password_key)).toString();
        try
        {
            UserTO userTO = new UserTO();
            userTO.setEmail(email);
            userTO.setPassword(password);
            userTO.setPasswordConfirmation(password);
            UserSignInTask task = new UserSignInTask(userTO);
            task.addHandler(this);
            return task;
        }
        catch (Exception e)
        {
            return null;
            //TODO HANDLE error
        }

    }

    @Override
    protected int getFragmentId() {
        return R.id.signin_fragment;
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
        helper.showLongDurationMessage(this,"You had successfully signed in!");
    }


}
