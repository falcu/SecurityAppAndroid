package com.example.guido.securityapp.exceptions;

/**
 * Created by guido on 1/17/15.
 */
public class MissingLoggedUserException extends Exception{

    public MissingLoggedUserException(String message)
    {
       super(message);
    }
}
