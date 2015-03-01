package com.example.guido.securityapp.builders.services;


import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.http_requests.BuilderGetLocalitiesRequest;
import com.example.guido.securityapp.builders.http_requests.BuilderSetLocalityClassificationRequest;
import com.example.guido.securityapp.converters.ComposedConverter;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.converters.json.ArrayToList;
import com.example.guido.securityapp.converters.json.HttpLocalitiesToLocalitiesList;
import com.example.guido.securityapp.converters.json.HttpLocalityToLocality;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.converters.json.ObjectToJson;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IDataLoader;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.restful.GetHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceLocalities;
import com.example.guido.securityapp.services.ServiceSetLocalityClassification;
import com.example.guido.securityapp.services.ServiceStore;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;

import java.lang.reflect.Array;

public class BuilderServiceLocalities
{
    private static ServiceLocalities serviceLocalities = null;
    private static ServiceSetLocalityClassification serviceSetLocalityClassification = null;

    public static ServiceLocalities buildServiceLocalities()
    {
        if(serviceLocalities == null)
        {
            HttpRequestService httpRequestService = new HttpRequestService(new GetHttpManager(), new BuilderGetLocalitiesRequest());

            String localitiesStoreKey = MyApplication.getContext().getResources().getString(R.string.localities_store_key);
            ComposedConverter saveConverter = new ComposedConverter(new ObjectToJson());
            saveConverter.setComposedConverter(new ComposedConverter(new HttpLocalitiesToLocalitiesList()));
            ComposedConverter loadConverter = new ComposedConverter(new ArrayToList());
            loadConverter.setComposedConverter(new ComposedConverter(new JsonToObject(Locality[].class)));
            IDataStore store = new ServiceStore(localitiesStoreKey,loadConverter,saveConverter);

            serviceLocalities = new ServiceLocalities(httpRequestService,store);
        }

        return serviceLocalities;
    }

    public static ServiceSetLocalityClassification buildServiceSetLocalityClassification()
    {
        if(serviceSetLocalityClassification == null)
        {
            HttpRequestService httpRequestService = new HttpRequestService(new PutHttpManager(), new BuilderSetLocalityClassificationRequest());

            Converter converter = new HttpLocalityToLocality();

            String localitiesStoreKey = MyApplication.getContext().getResources().getString(R.string.localities_store_key);
            ComposedConverter loadConverter = new ComposedConverter(new ArrayToList());
            loadConverter.setComposedConverter(new ComposedConverter(new JsonToObject(Locality[].class)));
            IDataStore store = new ServiceStore(localitiesStoreKey, loadConverter,new ObjectToJson());

            IEventAggregator eventAggregator = FactoryEventAggregator.getInstance();

            serviceSetLocalityClassification = new ServiceSetLocalityClassification(httpRequestService,store,converter,eventAggregator, new ServiceBadHttpRequestAnalyzer());


        }
        return serviceSetLocalityClassification;
    }

    public static void setServiceLocalities(ServiceLocalities service)
    {
        serviceLocalities = service;
    }

    public static void setServiceSetLocalityClassification(ServiceSetLocalityClassification service)
    {
        serviceSetLocalityClassification = service;
    }

    public static void clean()
    {
        serviceLocalities = null;
        serviceSetLocalityClassification = null;
    }

}