package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.builders.http_requests.BuilderPanicRequest;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceNotification;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;
import com.example.guido.securityapp.services.http_analyzers.ServiceMessageHttpRequestAnalyzer;

/**
 * Created by guido on 2/14/15.
 */
public class BuilderServiceNotification {

    private static ServiceNotification service = null;

    public static ServiceNotification build()
    {
        if(service==null)
        {
            service = new ServiceNotification(new HttpRequestService(new PostHttpManager(),new BuilderPanicRequest()),new ServiceBadHttpRequestAnalyzer(), new ServiceMessageHttpRequestAnalyzer());
        }
        return service;
    }
}
