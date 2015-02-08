package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.builders.BuilderServiceUserToken;

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
            BuilderGroupService.buildGroupInformationService().updateGroupInformation(token);
        }
        catch (Exception e)
        {
            result.setError(e.getMessage());
        }
        return result;

    }
}
