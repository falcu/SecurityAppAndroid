package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.IFragmentOptions;
import com.example.guido.securityapp.interfaces.IFragmentResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements IFragmentOptions, View.OnClickListener{

    private RadioGroup optionsGroup;
    private List<Option> options;


    public OptionsFragment() {
        options = new ArrayList<>(10);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_options, container, false);
        optionsGroup = (RadioGroup) theView.findViewById(R.id.options_group);
        return theView;
    }


    @Override
    public void addOption(Option option) {
        RadioButton newOption = new RadioButton(getActivity());
        options.add(option);
        int id = options.size();
        newOption.setId(id);
        newOption.setText(option.getText());
        optionsGroup.addView(newOption);
        setupClickListener(getView(),id);
    }

    private void setupClickListener(View theView, int childViewId) {
        View childView = theView.findViewById(childViewId);
        childView.setOnClickListener(this);
    }


    @Override
    public void setOptions(List<Option> optionsTexts) {
        removeAllOptions();
        for(Option option: optionsTexts)
            addOption(option);

    }

    private void removeAllOptions()
    {
        optionsGroup.removeAllViews();
        options = null;
        options = new ArrayList<>(10);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Option selectedOption = options.get(id-1);
        try
        {
            IFragmentResultHandler handler = (IFragmentResultHandler) getActivity();
            handler.handle(selectedOption.getKey());
        }
        catch (ClassCastException e)
        {
            return;
        }
    }
}


