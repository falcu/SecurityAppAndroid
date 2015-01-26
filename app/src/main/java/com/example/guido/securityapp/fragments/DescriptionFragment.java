package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.IFragmentVisibility;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment implements IFragmentVisibility {

    private TextView descriptionTextView;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_description, container, false);
        descriptionTextView = (TextView) theView.findViewById(R.id.description);
        return theView;
    }

    public void setDescription(String description)
    {
        descriptionTextView.setText(description);
    }


    @Override
    public void show() {
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        getView().setVisibility(View.GONE);
    }
}
