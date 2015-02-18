package com.example.guido.securityapp.fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.R;

import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IListFragment;


public class CustomListFragment extends ListFragment implements IListFragment {


    IBuildAdapter adapterBuilder = new NullBuilderAdapter();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            setListAdapter(adapterBuilder.build());
        }
        catch (Exception e)
        {
            IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
            handler.handle(e);
        }
    }


    @Override
    public void setBuilderAdapter(IBuildAdapter builder) {
        this.adapterBuilder = builder;
    }

    @Override
    public void update() {
        try {

            setListAdapter(adapterBuilder.build());
        }
        catch (Exception e)
        {
            IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
            handler.handle(e);
        }
    }

    public class NullBuilderAdapter implements IBuildAdapter
    {

        @Override
        public BaseAdapter build() {
            return new BaseAdapter() {
                @Override
                public int getCount() {
                    return 0;
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return null;
                }
            };
        }
    }
}
