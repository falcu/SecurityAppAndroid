package com.example.guido.securityapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.interfaces.IGetFilterHint;
import com.example.guido.securityapp.interfaces.IGetFilterLabel;
import com.example.guido.securityapp.interfaces.IListMenuHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomListWithFilterFragment extends CustomListFragment {

    protected TextView filterLabel;
    protected EditText filterText;


    public CustomListWithFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_custom_list_with_filter, container, false);
        filterLabel = (TextView) theView.findViewById(R.id.filter_label);

        filterText = (EditText) theView.findViewById(R.id.filter_text);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try
                {
                   Filter filter = ((Filterable)adapter).getFilter();
                   filter.filter(s);
                }
                catch (ClassCastException e){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return theView;

    }

    @Override
    protected void setAdapterOnList() {
        super.setAdapterOnList();
        setFilterLabel();
        setFilterHint();
    }

    protected void setFilterLabel()
    {
        try
        {
            String label = ((IGetFilterLabel)adapter).getFilterLabel();
            filterLabel.setText(label);
        }
        catch (Exception e)
        {
            filterLabel.setText("Filter");
        }
    }

    protected void setFilterHint()
    {
        try
        {
            String hint = ((IGetFilterHint)adapter).getFilterHint();
            filterText.setHint(hint);
        }
        catch (Exception e)
        {
            filterText.setHint("Name");
        }
    }


}
