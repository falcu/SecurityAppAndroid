package com.example.guido.securityapp.interfaces;

import com.example.guido.securityapp.models.SignedUser;
import com.example.guido.securityapp.models.UserTO;

/**
 * Created by guido on 1/17/15.
 */
public interface ISignService {

    public boolean isUserSingedIn() throws Exception;
    public void sign(UserTO userTO) throws Exception;
    public SignedUser getSignedUser() throws Exception;
}
