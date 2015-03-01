package com.example.guido.securityapp.services;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityClassificationTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/28/15.
 */
public class ServiceSetLocalityClassification {

    private HttpRequestService httpRequestService;
    private Converter httpToLocalityConverter;

    public ServiceSetLocalityClassification(HttpRequestService httpRequestService, Converter httpToLocalityConverter) {
        this.httpRequestService = httpRequestService;
        this.httpToLocalityConverter = httpToLocalityConverter;
    }

    public Locality updateLocalityClassification(LocalityClassificationTO localityClassificationTO) throws Exception
    {
        String data = httpRequestService.request(localityClassificationTO);
        return (Locality) httpToLocalityConverter.convert(data);
    }
}
