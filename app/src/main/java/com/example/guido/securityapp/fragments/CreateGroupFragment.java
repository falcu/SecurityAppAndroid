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
import com.example.guido.securityapp.interfaces.IFragmentVisibility;
import com.example.guido.securityapp.interfaces.IValidate;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment implements IFragmentVisibility, View.OnClickListener {

    private EditText groupNameView;
    private IValidate groupNameValidator;

    public CreateGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_create_group, container, false);
        groupNameView = (EditText) theView.findViewById(R.id.group_name);
        BuilderValidator builderValidator = new BuilderValidator();
        groupNameValidator = builderValidator.buildValidator(BuilderValidator.ValidatorType.GROUP_NAME);

        Button button = (Button) theView.findViewById(R.id.create_group_action);
        button.setOnClickListener(this);
        return theView;
    }


    @Override
    public void show() {
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        getView().setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        groupNameView.setError(null);
        String groupName = groupNameView.getText().toString();
        String error = groupNameValidator.getError(groupName);
        if(!error.isEmpty())
        {
            groupNameView.setError(error);
            groupNameView.requestFocus();
        }
    }
}
