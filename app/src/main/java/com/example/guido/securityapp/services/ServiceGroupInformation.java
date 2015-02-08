package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/2/15.
 */
public class ServiceGroupInformation implements IServiceError{

    private IDataStore store;
    private HttpRequestService httpService;
    private IErrorAnalyzer errorAnalyzer;

    public ServiceGroupInformation(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer)
    {
        this.store = store;
        this.httpService = httpService;
        this.errorAnalyzer = errorAnalyzer;
    }

    public void updateGroupInformation(String userToken) throws Exception
    {
        String data = httpService.request(userToken);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveError())
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

    @Override
    public boolean wasRequestWithError() {
        return errorAnalyzer.didLastDataHaveError();
    }

    @Override
    public String getLastErrorMessage() {
        return errorAnalyzer.getLastErrorMessage();
    }
}
