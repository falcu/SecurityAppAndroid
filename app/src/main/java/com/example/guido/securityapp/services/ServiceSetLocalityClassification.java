package com.example.guido.securityapp.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityClassificationTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/28/15.
 */
public class ServiceSetLocalityClassification extends ServiceBase {

    private Converter httpToLocalityConverter;
    private IEventAggregator eventAggregator;

    public ServiceSetLocalityClassification(HttpRequestService httpRequestService, IDataStore store, Converter httpToLocalityConverter, IEventAggregator eventAggregator, IMessageAnalyzer errorAnalyzer) {
        super(store,httpRequestService,errorAnalyzer);
        this.httpToLocalityConverter = httpToLocalityConverter;
        this.eventAggregator = eventAggregator;
    }

    public Locality updateLocalityClassification(LocalityClassificationTO localityClassificationTO) throws Exception
    {
        String data = httpService.request(localityClassificationTO);
        errorAnalyzer.analyze(data);
        Locality updatedLocality = null;
        if(!errorAnalyzer.didLastDataHaveMessage())
        {
            updatedLocality = (Locality) httpToLocalityConverter.convert(data);
            updateStoredLocalities(updatedLocality);
            eventAggregator.publish(MyApplication.getContext().getResources().getString(R.string.UPDATE_SINGLE_LOCALITY), updatedLocality);
        }

        return updatedLocality;
    }

    private void updateStoredLocalities(Locality locality) throws Exception
    {
        List<Locality> localities = (List<Locality>) store.load();
        List<Locality> updatedLocalities = new ArrayList<>();

        for (Locality l : localities) {
            if (l.getId() == locality.getId()) {
                updatedLocalities.add(locality);
            } else {
                updatedLocalities.add(l);
            }
        }
        store.save(updatedLocalities);
    }
}
