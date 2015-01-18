package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.ISignService;
import com.example.guido.securityapp.models.SignedUser;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.restful.services.SignHttpService;

/**
 * Created by guido on 1/17/15.
 */
public class ServiceSign implements ISignService {

    private IDataStore store;
    private SignHttpService httpService;

    public ServiceSign(IDataStore store, SignHttpService httpService)
    {
        this.store = store;
        this.httpService = httpService;
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
        store.save(data);

    }

    @Override
    public SignedUser getSignedUser() throws Exception{
        Object maybeSignedUser = store.load();
        SignedUser signedUser = (SignedUser)maybeSignedUser;
        return signedUser;
    }
}
