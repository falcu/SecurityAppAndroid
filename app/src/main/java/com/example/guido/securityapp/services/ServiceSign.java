package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.models.SignedUser;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.services.SignHttpService;

/**
 * Created by guido on 1/17/15.
 */
public class ServiceSign implements ISignService, IServiceError {

    private IDataStore store;
    private SignHttpService httpService;
    private IErrorAnalyzer errorAnalyzer;

    public ServiceSign(IDataStore store, SignHttpService httpService, IErrorAnalyzer errorAnalyzer)
    {
        this.store = store;
        this.httpService = httpService;
        this.errorAnalyzer = errorAnalyzer;
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

        String data = httpService.sign(userTO);
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

    @Override
    public boolean wasRequestWithError() {
        return errorAnalyzer.didLastDataHaveError();
    }

    @Override
    public String getLastErrorMessage() {
        return errorAnalyzer.getLastErrorMessage();
    }
}
