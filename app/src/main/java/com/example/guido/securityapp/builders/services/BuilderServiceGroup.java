package com.example.guido.securityapp.builders.services;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.http_requests.BuilderCreateGroupRequest;
import com.example.guido.securityapp.builders.http_requests.BuilderGetGroupInfoRequest;
import com.example.guido.securityapp.builders.http_requests.BuilderQuitGroupRequest;
import com.example.guido.securityapp.builders.http_requests.BuilderRenameGroupRequest;
import com.example.guido.securityapp.converters.json.HttpCreateGroupResponseToJson;
import com.example.guido.securityapp.converters.json.HttpGroupInfoResponseToJson;
import com.example.guido.securityapp.converters.json.HttpOperationMemberResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataRemover;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IMessageAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.restful.GetHttpManager;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceCreateGroup;
import com.example.guido.securityapp.services.ServiceDeleteData;
import com.example.guido.securityapp.services.ServiceQuitGroup;
import com.example.guido.securityapp.services.ServiceRenameGroup;
import com.example.guido.securityapp.services.http_analyzers.ServiceBadHttpRequestAnalyzer;
import com.example.guido.securityapp.services.ServiceGroupInformation;
import com.example.guido.securityapp.services.ServiceStore;
import com.example.guido.securityapp.services.http_analyzers.ServiceMessageHttpRequestAnalyzer;

import java.util.HashMap;

/**
 * Created by guido on 2/1/15.
 */
public class BuilderServiceGroup {

    private static HashMap<GroupServices,Object> services = new HashMap();

    public static ServiceCreateGroup buildCreateService()
    {
        addCreateService();
        return (ServiceCreateGroup)services.get(GroupServices.CREATE);
    }

    public static ServiceGroupInformation buildGroupInformationService()
    {
        addGroupInformationService();
        return (ServiceGroupInformation)services.get(GroupServices.GROUP_INFORMATION);
    }

    public static ServiceRenameGroup builderRenameGroupService()
    {
        addRenameGroupService();
        return (ServiceRenameGroup) services.get(GroupServices.RENAME);
    }

    public static ServiceQuitGroup builderQuitGroupService()
    {
        addQuitGroupService();
        return (ServiceQuitGroup) services.get(GroupServices.QUIT);
    }

    private static void addCreateService()
    {
        if(!services.containsKey(GroupServices.CREATE))
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpOperationMemberResponseToJson());
            HttpRequestService service = new HttpRequestService(new PostHttpManager(), new BuilderCreateGroupRequest());
            services.put(GroupServices.CREATE,new ServiceCreateGroup(store,service,new ServiceBadHttpRequestAnalyzer(), new ServiceMessageHttpRequestAnalyzer()));
        }
    }

    private static void addGroupInformationService()
    {
        if(!services.containsKey(GroupServices.GROUP_INFORMATION))
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpGroupInfoResponseToJson());
            HttpRequestService service = new HttpRequestService(new GetHttpManager(), new BuilderGetGroupInfoRequest());
            IMessageAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            services.put(GroupServices.GROUP_INFORMATION,new ServiceGroupInformation(store,service,errorAnalyzer));
        }
    }

    private static void addRenameGroupService()
    {
        if(!services.containsKey(GroupServices.RENAME))
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new HttpOperationMemberResponseToJson());
            HttpRequestService service = new HttpRequestService(new PutHttpManager(), new BuilderRenameGroupRequest());
            services.put(GroupServices.RENAME,new ServiceRenameGroup(store,service,new ServiceBadHttpRequestAnalyzer(),new ServiceMessageHttpRequestAnalyzer()));
        }
    }

    private static void addQuitGroupService()
    {
        if(!services.containsKey(GroupServices.QUIT))
        {
            IDataRemover remover = new ServiceDeleteData(MyApplication.getContext().getString(R.string.group_store_key));
            HttpRequestService service = new HttpRequestService(new PutHttpManager(), new BuilderQuitGroupRequest());
            services.put(GroupServices.QUIT,new ServiceQuitGroup(new ServiceMessageHttpRequestAnalyzer(),service,new ServiceBadHttpRequestAnalyzer(),remover));
        }
    }

    private enum GroupServices
    {
        CREATE, GROUP_INFORMATION, RENAME, QUIT
    }
}
