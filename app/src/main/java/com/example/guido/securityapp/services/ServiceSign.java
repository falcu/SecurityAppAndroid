package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.models.SignedUser;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 1/17/15.
 */
public class ServiceSign extends ServiceBase implements ISignService {

    public ServiceSign(IDataStore store, HttpRequestService httpService, IErrorAnalyzer errorAnalyzer)
    {
        super(store,httpService,errorAnalyzer);
    }
    @Override
    public boolean isUserSingedIn() {
        try {
            store.load();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void sign(UserTO userTO) throws Exception{

        String data = httpService.request(userTO);
        errorAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveError())
            store.save(data);

    }

    @Override
    public SignedUser getSignedUser() throws Exception{
        Object maybeSignedUser = store.load();
        SignedUser signedUser = (SignedUser)maybeSignedUser;
        return signedUser;
    }

}
