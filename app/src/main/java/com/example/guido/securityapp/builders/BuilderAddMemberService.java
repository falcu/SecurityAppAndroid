package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.HttpAddMemberResponseToJson;
import com.example.guido.securityapp.converters.json.HttpCreateGroupResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceAddMember;
import com.example.guido.securityapp.services.ServiceCreateGroup;
import com.example.guido.securityapp.services.ServiceStore;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;

/**
 * Created by guido on 2/9/15.
 */
public class BuilderAddMemberService {

    private static ServiceAddMember service = null;

    public static ServiceAddMember build()
    {
        return  makeServiceIfNull();
    }

    private static ServiceAddMember makeServiceIfNull()
    {
        if(service==null)
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpAddMemberResponseToJson());
            HttpRequestService httpService = new HttpRequestService(new PutHttpManager(), new BuilderAddMemberRequest());
            IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            service = new ServiceAddMember(store,httpService,errorAnalyzer);
        }
        return service;
    }
}
