package com.example.guido.securityapp.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.BuilderRegisterIdService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.builders.SignOptions;
import com.example.guido.securityapp.interfaces.IFragmentGetData;
import com.example.guido.securityapp.interfaces.ISetFragmentError;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mNameView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameView = (EditText) findViewById(R.id.name);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mNameView.setError(null);
        IFragmentGetData fragmentData = (IFragmentGetData)this.getFragmentManager().findFragmentById(R.id.signin_fragment);
        // Store values at the time of the login attempt.
        String email = fragmentData.getData().get(getString(R.string.email_key)).toString();
        String password = fragmentData.getData().get(getString(R.string.password_key)).toString();
        String name = mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ISetFragmentError fragmentError = (ISetFragmentError)this.getFragmentManager().findFragmentById(R.id.signin_fragment);
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            fragmentError.setError(getString(R.string.password_error_key),getString(R.string.error_invalid_password));
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            fragmentError.setError(getString(R.string.email_error_key),getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            fragmentError.setError(getString(R.string.email_error_key),getString(R.string.error_invalid_email));
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(name,email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mEmail;
        private final String mPassword;


        UserLoginTask(String name,String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = false;

            try {
                ServiceRegisterId serviceRegisterId = BuilderRegisterIdService.buildRegisterIdService();
                serviceRegisterId.setRegistrationIdWithErrorDialog(SignUpActivity.this);
                String regId = serviceRegisterId.getRegistrationId();
                if(!regId.isEmpty())
                {
                    UserTO userTransferObject = new UserTO();
                    userTransferObject.setName(mName);
                    userTransferObject.setEmail(mEmail);
                    userTransferObject.setPassword(mPassword);
                    userTransferObject.setRegistrationId(regId);
                    ServiceSign serviceSign = BuilderSignService.buildServiceSign(SignOptions.SIGN_UP);
                    serviceSign.sign(userTransferObject);
                    result = true;

                }
            } catch (Exception e) {
            }

            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {

                //Set server error
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



