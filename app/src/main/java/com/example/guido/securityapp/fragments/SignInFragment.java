package com.example.guido.securityapp.fragments;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.IFragmentGetData;
import com.example.guido.securityapp.interfaces.ISetFragmentError;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.interfaces.IValidateFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ISetFragmentError, IFragmentGetData, IValidateFragment {

    protected AutoCompleteTextView emailView;
    protected EditText passwordView;
    protected IValidate emailValidator;
    protected IValidate passwordValidator;
    protected BuilderValidator builderValidator;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initialize(view);
        return view;
    }

    protected void initialize(View view)
    {
        emailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        passwordView = (EditText) view.findViewById(R.id.password);
        builderValidator = makeValidatorBuilder();
        populateAutoComplete();
        setValidators();
    }

    protected void setValidators()
    {
        emailValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.EMAIL);
        passwordValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.PASSWORD);
    }

    protected BuilderValidator makeValidatorBuilder()
    {
        return new BuilderValidator();
    }

    protected void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.getActivity(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    protected void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this.getActivity(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void setError(String key, String error) {

        if(key.equals(MyApplication.getContext().getString(R.string.email_error_key).toString()))
        {
            emailView.setError(error);
            emailView.requestFocus();
        }
        else if(key.equals(MyApplication.getContext().getString(R.string.password_error_key).toString()))
        {
            passwordView.setError(error);
            passwordView.requestFocus();
        }
    }

    @Override
    public void clearErrors() {
        emailView.setError(null);
        passwordView.setError(null);
    }

    @Override
    public boolean validateFragment() {
        boolean passwordIsCorrect = validatePassword();
        boolean emailIsCorrect = validateEmail();
        return emailIsCorrect && passwordIsCorrect;
    }

    protected boolean validatePassword()
    {
        String passwordError = passwordValidator.getError(passwordView.getText().toString());

        if(!passwordError.isEmpty())
        {
            passwordView.setError(passwordError);
            passwordView.requestFocus();
            return false;
        }
        return true;
    }

    protected boolean validateEmail()
    {
        String emailError = emailValidator.getError(emailView.getText().toString());
        if(!emailError.isEmpty())
        {
            emailView.setError(emailError);
            emailView.requestFocus();
            return false;
        }
        return true;
    }

    protected interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    @Override
    public HashMap<String, Object> getData() {

        HashMap<String,Object> data = new HashMap<>();
        data.put(getString(R.string.email_key), emailView.getText().toString());
        data.put(getString(R.string.password_key), passwordView.getText().toString());

        return data;
    }
}
