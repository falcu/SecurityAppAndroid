package com.example.guido.securityapp.services;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class ServiceLocalities {

    protected HttpRequestService httpRequestService;
    protected IDataStore store;

    public ServiceLocalities(HttpRequestService httpRequestService, IDataStore store) {
        this.httpRequestService = httpRequestService;
        this.store = store;
    }

    public void updateLocalities(TokenTO tokenTO) throws Exception
    {
            String data = httpRequestService.request(tokenTO);
            store.save(data);

    }

    public void saveLocalities(List<Locality> updatedLocalities)
    {
        try {
            store.save(updatedLocalities);
        } catch (Exception e) {
            //TODO HANDLE
        }
    }

    public List<Locality> getLocalities()
    {
        List<Locality> localities;
        try {
            localities = (List<Locality>) store.load();
        } catch (Exception e) {
            localities = new ArrayList<>();
        }
        return localities;
    }
}
