package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceMessage;
import com.example.guido.securityapp.models.RenameGroupTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/15/15.
 */
public class ServiceRenameGroup extends ServiceBase implements IServiceMessage{
    protected IMessageAnalyzer messageAnalyzer;

    public ServiceRenameGroup(IDataStore store, HttpRequestService httpService, IMessageAnalyzer errorAnalyzer, IMessageAnalyzer messageAnalyzer) {
        super(store, httpService, errorAnalyzer);
        this.messageAnalyzer = messageAnalyzer;
    }

    public void renameGroup(RenameGroupTO renameGroupTO) throws Exception
    {
        String data = httpService.request(renameGroupTO);
        errorAnalyzer.analyze(data);
        messageAnalyzer.analyze(data);
        if(!errorAnalyzer.didLastDataHaveMessage())
            store.save(data);
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
