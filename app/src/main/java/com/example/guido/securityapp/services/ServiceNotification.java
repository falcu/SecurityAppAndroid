package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.IServiceMessage;
import com.example.guido.securityapp.models.PanicTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/14/15.
 */
public class ServiceNotification implements IServiceError, IServiceMessage {
    protected HttpRequestService httpService;
    protected IMessageAnalyzer errorAnalyzer;
    protected IMessageAnalyzer messageAnalyzer;

    public ServiceNotification(HttpRequestService service, IMessageAnalyzer errorAnalyzer, IMessageAnalyzer messageAnalyzer)
    {
        this.httpService = service;
        this.errorAnalyzer = errorAnalyzer;
        this.messageAnalyzer = messageAnalyzer;
    }

    public void sendNotification(PanicTO panicTO)throws Exception{
        String data = httpService.request(panicTO);
        errorAnalyzer.analyze(data);
        messageAnalyzer.analyze(data);
    }

    @Override
    public boolean wasRequestWithError() {
        return this.errorAnalyzer.didLastDataHaveMessage();
    }

    @Override
    public String getLastErrorMessage() {
        return this.errorAnalyzer.getLastMessage();
    }

    @Override
    public boolean wasRequestWithMessage() {
        return messageAnalyzer.didLastDataHaveMessage();
    }

    @Override
    public String getLastMessage() {
        return messageAnalyzer.getLastMessage();
    }
}
