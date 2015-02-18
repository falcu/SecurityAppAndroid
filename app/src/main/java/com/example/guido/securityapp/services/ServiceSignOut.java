package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataRemover;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.IServiceMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/17/15.
 */
public class ServiceSignOut implements IServiceError {

    protected List<IDataRemover> removers;
    protected boolean hadError = false;
    protected String messageError = "";

    public ServiceSignOut(List<IDataRemover>  remover)
    {
        this.removers = remover;
    }

    public ServiceSignOut()
    {
        this.removers = new ArrayList<>();
    }

    public void addRemover(IDataRemover remover)
    {
        this.removers.add(remover);
    }

    public void signOut()
    {
        hadError = false;
        try
        {
           int i = 0;
           while(i<removers.size())
           {
               removers.get(i).delete();
               i++;
           }

        }
        catch (Exception e)
        {
            hadError = true;
            messageError = e.getMessage();
        }
    }

    @Override
    public boolean wasRequestWithError() {
        return hadError;
    }

    @Override
    public String getLastErrorMessage() {
        return messageError;
    }
}