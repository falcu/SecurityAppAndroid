package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataRemover;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.IServiceMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/17/15.
 */
public class ServiceDeleteAll implements IServiceError {

    protected List<IDataRemover> removers;
    protected boolean hadError = false;
    protected String messageError = "";

    public ServiceDeleteAll(List<IDataRemover> remover)
    {
        this.removers = remover;
    }

    public ServiceDeleteAll()
    {
        this.removers = new ArrayList<>();
    }

    public void addRemover(IDataRemover remover)
    {
        this.removers.add(remover);
    }

    public void deleteAll()
    {
        hadError = false;

           int i = 0;
           while(i<removers.size())
           {
               try
               {
                   removers.get(i).delete();
               }
               catch (Exception e)
               {
                   hadError = true;
                   messageError = e.getMessage();
               }
               i++;
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
