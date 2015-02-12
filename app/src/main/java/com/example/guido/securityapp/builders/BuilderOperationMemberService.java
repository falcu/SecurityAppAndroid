package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.HttpOperationMemberResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceOperationToMember;
import com.example.guido.securityapp.services.ServiceStore;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;

import java.util.HashMap;

/**
 * Created by guido on 2/9/15.
 */
public class BuilderOperationMemberService {

    private static HashMap<MemberOperation,ServiceOperationToMember> services = new HashMap<>();

    public static ServiceOperationToMember build(MemberOperation operation)
    {
        checkOperation(operation);
        return services.get(operation);
    }

    private static void checkOperation(MemberOperation operation)
    {
        switch (operation){
            case ADD:
                includeAddOperationIfNeeded();
                break;
            case REMOVE:
                includeRemoveOperationIfNeeded();
                break;
        }
    }

    private static void includeAddOperationIfNeeded()
    {
        if(!services.containsKey(MemberOperation.ADD))
        {
            ServiceOperationToMember addService;
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpOperationMemberResponseToJson());
            HttpRequestService httpService = new HttpRequestService(new PutHttpManager(), new BuilderAddMemberRequest());
            IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            addService = new ServiceOperationToMember(store,httpService,errorAnalyzer);
            services.put(MemberOperation.ADD,addService);
        }
    }

    private static void includeRemoveOperationIfNeeded()
    {
        if(!services.containsKey(MemberOperation.REMOVE))
        {
            ServiceOperationToMember addService;
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpOperationMemberResponseToJson());
            HttpRequestService httpService = new HttpRequestService(new PutHttpManager(), new BuilderRemoveMemberRequest());
            IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            addService = new ServiceOperationToMember(store,httpService,errorAnalyzer);
            services.put(MemberOperation.REMOVE,addService);
        }
    }

    public enum MemberOperation
    {
        ADD,REMOVE
    }
}
