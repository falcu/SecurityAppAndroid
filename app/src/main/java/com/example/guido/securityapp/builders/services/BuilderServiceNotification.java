package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.builders.http_requests.BuilderNotificationRequest;
import com.example.guido.securityapp.builders.http_requests.BuilderPanicRequest;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceNotification;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;
import com.example.guido.securityapp.services.http_analyzers.ServiceMessageHttpRequestAnalyzer;

import java.util.HashMap;

/**
 * Created by guido on 2/14/15.
 */
public class BuilderServiceNotification {

    private static HashMap<NotificationType,ServiceNotification> services = new HashMap<>();

    public static ServiceNotification build(NotificationType type)
    {
         buildService(type);
        return services.get(type);
    }

    private static void buildService(NotificationType type)
    {
        if(!services.containsKey(type))
        {
            ServiceNotification service = null;
            switch (type)
            {
                case ALARM:
                    service = new ServiceNotification(new HttpRequestService(new PostHttpManager(),new BuilderPanicRequest()),new ServiceBadHttpRequestAnalyzer(), new ServiceMessageHttpRequestAnalyzer());
                    break;
                case NOTIFICATION:
                    service = new ServiceNotification(new HttpRequestService(new PutHttpManager(), new BuilderNotificationRequest()), new ServiceBadHttpRequestAnalyzer(), new ServiceMessageHttpRequestAnalyzer());
                    break;

            }
            services.put(type,service);
        }
    }

    public enum NotificationType
    {
        ALARM,NOTIFICATION
    }
}
