package com.example.guido.securityapp.restful;

import com.example.guido.securityapp.converters.Converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guido on 1/17/15.
 */
public class RequestPackage {

    private String uri;
    private String method = "GET";
    private Converter paramsConverter;
    private Map<String,String> headerProperties = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setConverter(Converter paramsCalculator) {
        this.paramsConverter = paramsCalculator;
    }

    public String getParams() throws Exception
    {
        return (String)paramsConverter.convert();
    }

    public void setHeaderProperty(String key,String value){
        headerProperties.put(key,value);
    }
    public String getHeaderProperty(String key){
        return headerProperties.get(key);
    }

    public Iterator<String> getHeaderKeys()
    {
        return headerProperties.keySet().iterator();
    }
}
