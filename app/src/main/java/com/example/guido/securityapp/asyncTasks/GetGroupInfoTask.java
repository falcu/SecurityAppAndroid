package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderServiceUserToken;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.services.ServiceGroupInformation;

/**
 * Created by guido on 2/5/15.
 */
public class GetGroupInfoTask extends AsynTaskWithHandlers {
    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            String token = BuilderServiceUserToken.build().getToken();
            ServiceGroupInformation service = BuilderGroupService.buildGroupInformationService();
            service.updateGroupInformation(new TokenTO(token));
            if(service.wasRequestWithError()){
                result.setError(service.getLastErrorMessage());
            }

        }
        catch (Exception e)
        {
            result.setError(e.getMessage());
        }
        return result;

    }
}
