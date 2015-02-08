package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.HttpCreateGroupResponseToJson;
import com.example.guido.securityapp.converters.json.HttpGroupInfoResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.restful.GetHttpManager;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceCreateGroup;
import com.example.guido.securityapp.services.ServiceBadHttpRequestAnalyzer;
import com.example.guido.securityapp.services.ServiceGroupInformation;
import com.example.guido.securityapp.services.ServiceStore;

import java.util.HashMap;

/**
 * Created by guido on 2/1/15.
 */
public class BuilderGroupService {

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

    private static void addCreateService()
    {
        if(!services.containsKey(GroupServices.CREATE))
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_key), new JsonToObject(Group.class),new HttpCreateGroupResponseToJson());
            HttpRequestService service = new HttpRequestService(new PostHttpManager(), new BuilderCreateGroupRequest());
            IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            services.put(GroupServices.CREATE,new ServiceCreateGroup(store,service,errorAnalyzer));
        }
    }

    private static void addGroupInformationService()
    {
        if(!services.containsKey(GroupServices.GROUP_INFORMATION))
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_key), new JsonToObject(Group.class),new HttpGroupInfoResponseToJson());
            HttpRequestService service = new HttpRequestService(new GetHttpManager(), new BuilderGetGroupInfoRequest());
            IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();
            services.put(GroupServices.GROUP_INFORMATION,new ServiceGroupInformation(store,service,errorAnalyzer));
        }
    }

    private enum GroupServices
    {
        CREATE, GROUP_INFORMATION
    }
}
