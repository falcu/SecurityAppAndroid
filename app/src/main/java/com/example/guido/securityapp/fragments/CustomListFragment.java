package com.example.guido.securityapp.fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guido.securityapp.R;

import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.interfaces.IFragmentExceptionHandler;
import com.example.guido.securityapp.interfaces.IListFragment;
import com.example.guido.securityapp.interfaces.IListMenuHandler;


public class CustomListFragment extends ListFragment implements IListFragment {


    protected IBuildAdapter adapterBuilder = new NullBuilderAdapter();
    protected BaseAdapter adapter = null;
    protected boolean activityWasCreated;
    protected IListMenuHandler menuHandler = null;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityWasCreated = false;
        return inflater.inflate(R.layout.fragment_list,container,false);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(adapter!=null)
        {
            try
            {
                ((AdapterView.OnItemClickListener)adapter).onItemClick(l,v,position,id);

            }
            catch (ClassCastException e)
            {
                //Do nothing as adapter can't handle click
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityWasCreated = true;
        setAdapterOnList();
        registerForContextMenu(getListView());
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(menuHandler!=null)
        {
            menuHandler.onListContextMenuCreated(menu,v,menuInfo);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(menuHandler!=null)
        {
            menuHandler.onItemOptionSelected(item);
            return true;
        }
        return false;
    }

    protected void setAdapterOnList()
    {
        if(adapterBuilder!=null)
        {
            try {

                adapter = adapterBuilder.buildAdapter();
                setListAdapter(adapter);
            }
            catch (Exception e)
            {
                IFragmentExceptionHandler handler = (IFragmentExceptionHandler) getActivity();
                handler.handle(e);
            }
        }
    }


    @Override
    public void setBuilderAdapter(IBuildAdapter builder) {
        this.adapterBuilder = builder;
        if(activityWasCreated)
        {
            setAdapterOnList();
        }
    }

    @Override
    public void setEmptyListText(String text) {
        TextView empty = (TextView) getView().findViewById(android.R.id.empty);
        empty.setText(text);
    }

    @Override
    public void setMenuHandler(IListMenuHandler menuHandler) {
        this.menuHandler= menuHandler;
    }

    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    public class NullBuilderAdapter implements IBuildAdapter
    {

        @Override
        public BaseAdapter buildAdapter() {
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
