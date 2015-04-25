package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.validators.PasswordPair;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends SignInFragment {

    protected EditText nameView;
    protected EditText passwordConfirmationView;
    protected IValidate nameValidator;
    protected IValidate passwordConfirmationValidator;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        super.initialize(theView);
        nameView = (EditText) theView.findViewById(R.id.name);
        passwordConfirmationView = (EditText) theView.findViewById(R.id.reentry_password);

        return theView;
    }

    @Override
    protected void setValidators() {
        super.setValidators();
        nameValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.NAME);
        passwordConfirmationValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.PASSWORD_CONFIRMATION);
    }

    @Override
    public boolean validateFragment() {
        boolean isReentryPasswordCorrect = validatePasswordConfirmation();
        boolean superValidations = super.validateFragment();
        boolean isNameCorrect = validateName();

        return superValidations & isNameCorrect && isReentryPasswordCorrect;
    }

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> data = super.getData();
        data.put(getString(R.string.name_key),nameView.getText().toString());
        data.put(getString(R.string.reentry_password_key), passwordConfirmationView.getText().toString());
        return data;
    }

    protected boolean validateName()
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

    protected boolean validatePasswordConfirmation()
    {
       passwordConfirmationView.setError(null);
        String password = passwordView.getText().toString();
        String passwordConfirmation = passwordConfirmationView.getText().toString();
        String error = passwordConfirmationValidator.getError(new PasswordPair(password,passwordConfirmation));
        if(!error.isEmpty())
        {
            passwordConfirmationView.setError(error);
            passwordConfirmationView.requestFocus();
            return false;
        }
        return true;
    }


}
