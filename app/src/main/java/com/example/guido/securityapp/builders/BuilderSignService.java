package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.json.HttpUserResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.interfaces.IErrorAnalyzer;
import com.example.guido.securityapp.models.SignedUser;
import com.example.guido.securityapp.restful.PostHttpManager;
import com.example.guido.securityapp.restful.PutHttpManager;
import com.example.guido.securityapp.restful.services.HttpRequestService;
import com.example.guido.securityapp.services.ServiceBadHttpRequestAnalyzer;
import com.example.guido.securityapp.services.ServiceSign;
import com.example.guido.securityapp.services.ServiceStore;

import java.util.HashMap;

/**
 * Created by guido on 1/18/15.
 */
public class BuilderSignService {

    private static HashMap<SignOptions,ServiceSign> services = new HashMap<>();

    public static ServiceSign buildServiceSign(SignOptions option)
    {
        if(!services.containsKey(option))
            build(option);

        return services.get(option);
    }

    private static void build(SignOptions option)
    {
        IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.signed_user_store_key),new JsonToObject(SignedUser.class),new HttpUserResponseToJson());
        HttpRequestService httpService = null;
        ServiceSign serviceSign = null;
        IErrorAnalyzer errorAnalyzer = new ServiceBadHttpRequestAnalyzer();

        switch (option)
        {
            case SIGN_IN:
                httpService = new HttpRequestService(new PutHttpManager(),new BuilderSignInUserRequest());
                break;
            case SIGN_UP:
                httpService = new HttpRequestService(new PostHttpManager(),new BuilderSignUpUserRequest());
                break;
        }
        serviceSign = new ServiceSign(store,httpService,errorAnalyzer);
        services.put(option,serviceSign);
    }

    public enum SignOptions {

        SIGN_IN,SIGN_UP
    }

}
