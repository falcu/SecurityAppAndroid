package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.asyncTasks.UserSignInTask;
import com.example.guido.securityapp.asyncTasks.UserSignUpTask;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.IValidate;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==MyApplication.getContext().getResources().getInteger(R.integer.ACTIVITY_FINISH))
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra(MyApplication.getContext().getString(R.string.IS_ACTIVITY_FINISH),false)) {
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
    protected  AsyncTask<Void, Void, TaskResult>  getSignTask() {
        String email = getFragmentData().getData().get(getString(R.string.email_key)).toString();
        String password = getFragmentData().getData().get(getString(R.string.password_key)).toString();
        UserSignInTask task = new UserSignInTask(email,password);
        task.addHandler(this);
       return task;
    }

    @Override
    protected int getFragmentId() {
        return R.id.signin_fragment;
    }

    @Override
    protected boolean validateFields() {
        return validateEmail() && validatePassword();
    }


}
