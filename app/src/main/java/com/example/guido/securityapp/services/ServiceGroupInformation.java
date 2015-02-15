package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/2/15.
 */
public class ServiceGroupInformation extends ServiceBase{


    public ServiceGroupInformation(IDataStore store, HttpRequestService httpService, IMessageAnalyzer errorAnalyzer)
    {
        super(store,httpService,errorAnalyzer);
    }

    public void updateGroupInformation(TokenTO userToken) throws Exception
    {
        String data = httpService.request(userToken);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveMessage())
            store.save(data);

    }

    public Group getGroup() throws Exception
    {
        Group maybeGroup = (Group) store.load();
        return maybeGroup;
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
