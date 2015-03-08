package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.services.ServiceGroupInformation;

/**
 * Created by guido on 2/5/15.
 */
public class GetGroupInfoTask extends AsynTaskWithHandlers {

    protected TokenTO tokenTO;

    public GetGroupInfoTask(TokenTO transferObject) throws Exception {
        super(transferObject);
    }

    public GetGroupInfoTask(){

    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            String token = BuilderServiceUserToken.build().getToken();
            ServiceGroupInformation service = BuilderServiceGroup.buildGroupInformationService();
            service.updateGroupInformation(new TokenTO(token));
            if(service.wasRequestWithError()){
                result.setError(new TaskError(service.getLastErrorMessage()));
            }

        }
        catch (Exception e)
        {
            result.setError(new TaskError(e.getMessage(),e));
        }
        return result;

    }

    @Override
    protected Class getTransferObjectType() {
        return TokenTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.tokenTO = (TokenTO) transferObject;
    }
}
