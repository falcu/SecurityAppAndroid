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
import com.example.guido.securityapp.asyncTasks.SetLocalityClassificationTask;
import com.example.guido.securityapp.asyncTasks.TaskResult;
import com.example.guido.securityapp.asyncTasks.UpdateLocalitiesTask;
import com.example.guido.securityapp.builders.adapters.BuilderLocalitiesAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceLocalities;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.LocalitiesClassificationToMenuITemData;
import com.example.guido.securityapp.helpers.MenuItemData;
import com.example.guido.securityapp.helpers.ToastHelper;
import com.example.guido.securityapp.interfaces.IListFragment;
import com.example.guido.securityapp.interfaces.IListMenuHandler;
import com.example.guido.securityapp.interfaces.IProgressBar;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityClassificationTO;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.services.ServiceLocalities;

import java.util.HashMap;
import java.util.List;

public class LocalitiesCustomizationActivity extends Activity implements ITaskHandler, IListMenuHandler {

    protected ServiceLocalities serviceLocalities;
    protected IListFragment listFragment;
    protected IProgressBar progressBar;;
    protected final String getLocalitiesTaskIdentifier = "get_localities";
    protected final String setLocalityClassificationTaskIdentifier = "set_locality";

    protected List<MenuItemData> itemsData;

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

        initializeConverter();

    }

    protected void initializeConverter()
    {
        Converter classificationToMenuItemData = new LocalitiesClassificationToMenuITemData();
        try {
            itemsData = (List<MenuItemData>) classificationToMenuItemData.convert(Locality.LocalityClassification.values());
        } catch (Exception e) {
            //TODO HANDLE
        }
    }

    protected void updateLocalities()
    {
        try {
            String token = BuilderServiceUserToken.build().getToken();
            AsynTaskWithHandlers asynTaskWithHandlers = new UpdateLocalitiesTask(new TokenTO(token));
            asynTaskWithHandlers.setResultIdentifier(getLocalitiesTaskIdentifier);
            asynTaskWithHandlers.addHandler(this);
            asynTaskWithHandlers.execute((Void)null);
        } catch (Exception e) {
            //TODO HANDLE
        }

    }

    @Override
    public void onPreExecute(String identifier) {
        if(identifier.equals(getLocalitiesTaskIdentifier))
        {
            progressBar.showProgress(true);
        }
    }

    @Override
    public void onPostExecute(TaskResult taskResult) {

        if(taskResult.getIdentifier().equals(getLocalitiesTaskIdentifier))
        {
            if(taskResult.isSuccesful())
            {
                List<Locality> localities = serviceLocalities.getLocalities();
                listFragment.setBuilderAdapter(new BuilderLocalitiesAdapter(this,localities));
            }
            progressBar.showProgress(false);
        }
        else if(taskResult.getIdentifier().equals(setLocalityClassificationTaskIdentifier))
        {
            Locality updatedLocality = (Locality) taskResult.getResult();
            ((LocalitiesAdapter) listFragment.getAdapter()).showProgressOnItemWithId(updatedLocality.getId(), false);
            ToastHelper toastHelper = new ToastHelper();
            String message = updatedLocality.getName() + " was updated!";
            toastHelper.showLongDurationMessage(this,message);
        }

    }

    @Override
    public void onCancelled(String identifier) {
        progressBar.showProgress(false);
    }

    @Override
    public void onListContextMenuCreated(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            long id = info.id;
            Locality.LocalityClassification classification =  ((LocalitiesAdapter) listFragment.getAdapter()).getLocality(id).getClassification();
            MenuItemData itemToSelect = null;

            for(MenuItemData itemData : itemsData)
            {
              MenuItem item =  menu.add(itemData.getGroupId(),itemData.getOrder(),itemData.getId(),itemData.getTitle());
              item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
              if(itemData.getClassification().equals(classification))
              {
                  itemToSelect = itemData;
              }

            }

            menu.setGroupCheckable(itemsData.get(0).getGroupId(),true,true);
            menu.getItem(itemToSelect.getOrder()).setChecked(true);
        } catch (ClassCastException e) {
            return;
        }
    }


    @Override
    public void onItemOptionSelected(MenuItem item) {

        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            int localityId =  (int)info.id;
            String token = BuilderServiceUserToken.build().getToken();
            Locality.LocalityClassification classification = findClassificationByOrder(item.getOrder());
            AsynTaskWithHandlers setLocalityClassificationTask = new SetLocalityClassificationTask(new LocalityClassificationTO(localityId,classification,token));
            setLocalityClassificationTask.addHandler(this);
            setLocalityClassificationTask.setResultIdentifier(setLocalityClassificationTaskIdentifier);
            ((LocalitiesAdapter) listFragment.getAdapter()).showProgressOnItemWithId(localityId, true);

            setLocalityClassificationTask.execute((Void)null);
        } catch (Exception e) {
            //TODO HANDLE
        }

    }

    private Locality.LocalityClassification findClassificationByOrder(int order)
    {
        Locality.LocalityClassification c = null;
        for(MenuItemData i : itemsData)
        {
            if(i.getOrder()==order)
            {
                c = i.getClassification();
            }
        }
        return c;
    }

}
