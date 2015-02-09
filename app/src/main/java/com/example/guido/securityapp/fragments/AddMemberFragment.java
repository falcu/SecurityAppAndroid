package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.BuilderValidator;
import com.example.guido.securityapp.interfaces.IValidate;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends BaseFragmentOption implements View.OnClickListener{

    private EditText userEmailEditText;
    private IValidate emailValidator;
    private Button addMemberButton;
    private int id;
    private String key;

    public AddMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_add_member, container, false);
        userEmailEditText = (EditText) theView.findViewById(R.id.member_email);
        emailValidator = makeBuilderValidator().buildValidator(BuilderValidator.ValidatorType.EMAIL);
        addMemberButton = (Button) theView.findViewById(R.id.add_member_action);
        addMemberButton.setOnClickListener(this);
        theView.setVisibility(View.GONE);
        return theView;
    }

    protected BuilderValidator makeBuilderValidator()
    {
        return new BuilderValidator();
    }


    @Override
    public void onClick(View v) {
        userEmailEditText.setError(null);
        String email = userEmailEditText.getText().toString();
        String error = emailValidator.getError(email);
        if(error.isEmpty())
        {

        }
        else
        {
            userEmailEditText.setError(error);
            userEmailEditText.requestFocus();
        }

    }

}
