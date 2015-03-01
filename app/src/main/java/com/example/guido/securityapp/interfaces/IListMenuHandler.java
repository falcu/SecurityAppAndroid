package com.example.guido.securityapp.interfaces;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by guido on 2/25/15.
 */
public interface IListMenuHandler {

    public void onListContextMenuCreated(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo);
    public void onItemOptionSelected(MenuItem item);
}
