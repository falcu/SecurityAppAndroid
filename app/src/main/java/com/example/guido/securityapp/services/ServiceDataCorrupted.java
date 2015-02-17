package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataLoader;
import com.example.guido.securityapp.interfaces.IDataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/17/15.
 */
public class ServiceDataCorrupted {

    private List<IDataLoader> loaders;

    public ServiceDataCorrupted()
    {
        loaders = new ArrayList<>();
    }

    public void addLoader(IDataLoader loader)
    {
        loaders.add(loader);
    }

    public boolean isDataCorrupted()
    {
        int i = 0;
        boolean corrupted = false;
        while(!corrupted && i<loaders.size())
        {
            try
            {
                loaders.get(i).load();
            }
            catch (Exception e)
            {
                corrupted = true;
            }
            i++;
        }
        return corrupted;
    }
}
