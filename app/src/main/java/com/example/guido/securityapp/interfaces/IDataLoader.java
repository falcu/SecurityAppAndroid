package com.example.guido.securityapp.interfaces;

import com.example.guido.securityapp.exceptions.MissingLoggedUserException;

/**
 * Created by guido on 1/17/15.
 */
public interface IDataLoader {

    public Object load() throws Exception;
}
