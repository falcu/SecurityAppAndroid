package com.example.guido.securityapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.adapters.LocalitiesAdapter;
import com.example.guido.securityapp.asyncTasks.AsynTaskWithHandlers;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.asyncTasks.UpdateLocalitiesTask;
import com.example.guido.securityapp.builders.adapters.BuilderLocalitiesAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceLocalities;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.interfaces.IListFragment;
import com.example.guido.securityapp.interfaces.IListMenuHandler;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.services.ServiceLocalities;

import java.util.List;

public class LocalitiesCustomizationActivity extends Activity implements ITaskHandler, IListMenuHandler {

    protected ServiceLocalities serviceLocalities;
    protected IListFragment listFragment;
    protected IProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_localities_customization);
        listFragment = (IListFragment) getFragmentManager().findFragmentById(R.id.localities_list_fragment);
        listFragment.setMenuHandler(this);
        View mainView = (View) findViewById(R.id.localities_view);
        progressBar = (IProgressBar) getFragmentManager().findFragmentById(R.id.progress_bar_fragment);
        progressBar.showProgress(false);
        progressBar.setControllableView(mainView);
        serviceLocalities = BuilderServiceLocalities.buildServiceLocalities();
        List<Locality> localities = serviceLocalities.getLocalities();
        if(localities.isEmpty())
        {
            //update localities from server
            updateLocalities();
        }
        else
        {
            listFragment.setBuilderAdapter(new BuilderLocalitiesAdapter(this,localities));
        }

    }

    protected void updateLocalities()
    {
        try {
            String token = null;
            token = BuilderServiceUserToken.build().getToken();
            AsynTaskWithHandlers asynTaskWithHandlers = new UpdateLocalitiesTask(new TokenTO(token));
            asynTaskWithHandlers.addHandler(this);
            asynTaskWithHandlers.execute((Void)null);
        } catch (Exception e) {
            //TODO HANDLE
        }

    }

    @Override
    public void onPreExecute() {
        progressBar.showProgress(true);
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {
        if(taskResult.isSuccesful())
        {
            List<Locality> localities = serviceLocalities.getLocalities();
            listFragment.setBuilderAdapter(new BuilderLocalitiesAdapter(this,localities));
        }
        progressBar.showProgress(false);
    }

    @Override
    public void onCancelled() {
        progressBar.showProgress(false);
    }

    @Override
    public void onListContextMenuCreated(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            MenuInflater inflater = this.getMenuInflater();
            inflater.inflate(R.menu.menu_localities, menu);
            menu.getItem(0).setChecked(true);
        } catch (ClassCastException e) {
            return;
        }
    }

    @Override
    public void onItemOptionSelected(MenuItem item) {
        long localityId = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).id;
        ((LocalitiesAdapter) listFragment.getAdapter()).showProgressOnItemWithId(localityId, true);
    }
}
