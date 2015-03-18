package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/2/15.
 */
public class ServiceGroupInformation extends ServiceBase{

    private IEventAggregator eventAggregator;

    public ServiceGroupInformation(IDataStore store, HttpRequestService httpService, IMessageAnalyzer errorAnalyzer, IEventAggregator eventAggregator)
    {
        super(store,httpService,errorAnalyzer);
        this.eventAggregator = eventAggregator;
    }

    public void updateGroupInformation(TokenTO userToken) throws Exception
    {
        String data = httpService.request(userToken);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveMessage())
        {
            store.save(data);
            Group group = getGroup();
            eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_GROUP),group);
        }

    }

    public Group getGroup() throws Exception
    {
        try
        {
           Group maybeGroup = (Group) store.load();
            return maybeGroup;
        }
        catch (Exception e)
        {
            throw new UnableToLoadGroupException("Unable to load group");
        }
    }

    public boolean belongsToGroup()
    {
        try
        {
            store.load();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
