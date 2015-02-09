package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/8/15.
 */
public abstract class ServiceBase implements IServiceError {

    protected IDataStore store;
    protected HttpRequestService httpService;
    protected IErrorAnalyzer errorAnalyzer;

    public ServiceBase(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer)
    {
        this.store = store;
        this.httpService = httpService;
        this.errorAnalyzer = errorAnalyzer;
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
