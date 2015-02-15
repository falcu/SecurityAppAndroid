package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/8/15.
 */
public abstract class ServiceBase implements IServiceError {

    protected IDataStore store;
    protected HttpRequestService httpService;
    protected IMessageAnalyzer errorAnalyzer;

    public ServiceBase(IDataStore store, HttpRequestService httpService, IMessageAnalyzer errorAnalyzer)
    {
        this.store = store;
        this.httpService = httpService;
        this.errorAnalyzer = errorAnalyzer;
    }

    @Override
    public boolean wasRequestWithError() {
        return errorAnalyzer.didLastDataHaveMessage();
    }

    @Override
    public String getLastErrorMessage() {
        return errorAnalyzer.getLastMessage();
    }
}
