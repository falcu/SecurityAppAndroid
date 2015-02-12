package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/8/15.
 */
public class ServiceOperationToMember extends ServiceBase{
    public ServiceOperationToMember(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer) {
        super(store, httpService, errorAnalyzer);
    }

    public void doOperation(NewMemberTO newMember) throws Exception
    {
        String data = httpService.request(newMember);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveError())
            store.save(data);
    }


}
