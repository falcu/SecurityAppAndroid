package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataRemover;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceError;
import com.example.guido.securityapp.interfaces.IServiceMessage;
import com.example.guido.securityapp.models.QuitGroupTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/16/15.
 */
public class ServiceQuitGroup implements IServiceError,IServiceMessage{

    protected HttpRequestService httpRequestService;
    protected IMessageAnalyzer errorAnalyzer;
    protected IMessageAnalyzer messageAnalyzer;
    protected IDataRemover dataRemover;

    public ServiceQuitGroup(IMessageAnalyzer messageAnalyzer, HttpRequestService httpRequestService, IMessageAnalyzer errorAnalyzer, IDataRemover dataRemover) {
        this.messageAnalyzer = messageAnalyzer;
        this.httpRequestService = httpRequestService;
        this.errorAnalyzer = errorAnalyzer;
        this.dataRemover = dataRemover;
    }

    public void quitGroup(QuitGroupTO quitGroupTO) throws Exception
    {
        String data = httpRequestService.request(quitGroupTO);
        errorAnalyzer.analyze(data);
        messageAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveMessage())
        {
            dataRemover.delete();
        }
    }

    @Override
    public boolean wasRequestWithError() {
        return errorAnalyzer.didLastDataHaveMessage();
    }

    @Override
    public String getLastErrorMessage() {
        return errorAnalyzer.getLastMessage();
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
