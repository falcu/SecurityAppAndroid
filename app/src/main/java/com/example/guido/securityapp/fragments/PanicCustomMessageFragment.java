package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.builders.services.BuilderServicePanicMessage;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.services.ServicePanicMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanicCustomMessageFragment extends Fragment implements View.OnClickListener   {

    protected Button saveAction;
    protected EditText messageTextView;
    protected ServicePanicMessage servicePanicMessage;


    public PanicCustomMessageFragment() {
       servicePanicMessage = BuilderServicePanicMessage.build();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_panic_custom_message, container, false);
        saveAction = (Button) theView.findViewById(R.id.save_custom_message);
        saveAction.setOnClickListener(this);
        messageTextView = (EditText) theView.findViewById(R.id.custom_message);
        updateText();
        return theView;
    }


    @Override
    public void onClick(View v) {
        String message = messageTextView.getText().toString();
        servicePanicMessage.saveMessage(message);
        updateText();
        ToastHelper toastHelper = new ToastHelper();
        toastHelper.showLongDurationMessage(getActivity(),"Changes saved!");
    }

    private void updateText()
    {
        messageTextView.setText(servicePanicMessage.loadMessage());
    }
}
