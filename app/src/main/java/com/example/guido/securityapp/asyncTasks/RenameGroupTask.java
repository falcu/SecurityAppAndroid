package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.UnableToRetreiveTokenException;
import com.example.guido.securityapp.models.RenameGroupTO;
import com.example.guido.securityapp.services.ServiceRenameGroup;

/**
 * Created by guido on 2/15/15.
 */
public class RenameGroupTask extends AsynTaskWithHandlers{
    protected RenameGroupTO renameGroupTO;

    public RenameGroupTask(RenameGroupTO renameGroupTO) throws Exception{
        super(renameGroupTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            String token = BuilderServiceUserToken.build().getToken();
            this.renameGroupTO.setToken(token);
            ServiceRenameGroup service = BuilderServiceGroup.builderRenameGroupService();
            service.renameGroup(renameGroupTO);
            if(service.wasRequestWithError())
            {
                result.setResult(service.getLastErrorMessage());
            }
            else if(service.wasRequestWithMessage())
            {
                result.setResult(service.getLastMessage());
            }

        }
        catch (Exception e)
        {
            result.setResult(e.getMessage());
        }
        return result;
    }

    @Override
    protected Class getTransferObjectType() {
        return RenameGroupTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.renameGroupTO = (RenameGroupTO) transferObject;
    }
}
