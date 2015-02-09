package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/1/15.
 */
public class ServiceCreateGroup extends ServiceBase{


    public ServiceCreateGroup(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer)
    {
       super(store,httpService,errorAnalyzer);
    }

    public void createGroup(CreateGroupTO createGroupTO) throws Exception
    {
        String data = httpService.request(createGroupTO);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveError())
            store.save(data);

    }
}
