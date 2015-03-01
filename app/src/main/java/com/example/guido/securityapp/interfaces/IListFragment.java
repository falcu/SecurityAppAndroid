package com.example.guido.securityapp.interfaces;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by guido on 2/16/15.
 */
public interface IListFragment {

    public void setBuilderAdapter(IBuildAdapter builder);
    public void setEmptyListText(String text);
    public void setMenuHandler(IListMenuHandler menuHandler);
    public BaseAdapter getAdapter();

}
