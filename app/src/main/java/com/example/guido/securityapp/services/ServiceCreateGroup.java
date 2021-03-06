package com.example.guido.securityapp.services;

import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.interfaces.IServiceMessage;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.restful.services.HttpRequestService;

/**
 * Created by guido on 2/1/15.
 */
public class ServiceCreateGroup extends ServiceBase implements IServiceMessage{

    protected IMessageAnalyzer messageAnalyzer;


    public ServiceCreateGroup(IDataStore store, HttpRequestService httpService, IMessageAnalyzer errorAnalyzer, IMessageAnalyzer messageAnalyzer)
    {
       super(store,httpService,errorAnalyzer);
        this.messageAnalyzer = messageAnalyzer;
    }

    public void createGroup(CreateGroupTO createGroupTO) throws Exception
    {
        String data = httpService.request(createGroupTO);
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
