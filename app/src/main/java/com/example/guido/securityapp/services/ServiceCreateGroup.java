package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/1/15.
 */
public class ServiceCreateGroup implements IServiceError{

    private IDataStore store;
    private HttpRequestService httpService;
    private IErrorAnalyzer errorAnalyzer;

    public ServiceCreateGroup(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer)
    {
        this.store = store;
        this.httpService = httpService;
        this.errorAnalyzer = errorAnalyzer;
    }

    public void createGroup(CreateGroupTO createGroupTO) throws Exception
    {
        String data = httpService.request(createGroupTO);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveError())
            store.save(data);

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
