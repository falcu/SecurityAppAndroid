package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.services.ServiceCreateGroup;

/**
 * Created by guido on 2/1/15.
 */
public class CreateGroupTask extends AsynTaskWithHandlers{
    private CreateGroupTO createGroupTO;

    public CreateGroupTask(Object createGroupTO) throws Exception
    {
        super(createGroupTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            ServiceCreateGroup service = BuilderServiceGroup.buildCreateService();
            service.createGroup(createGroupTO);
            if(service.wasRequestWithError())
            {
                result.setError(new TaskError(service.getLastErrorMessage()));
            }
            else if(service.wasRequestWithMessage())
            {
                result.setResult(service.getLastMessage());
            }
        }
        catch (Exception e)
        {
            result.setError(new TaskError("Unable to create group, try again"));
        }
        return result;
    }

    @Override
    protected Class getTransferObjectType() {
        return CreateGroupTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.createGroupTO = (CreateGroupTO) transferObject;
    }
}
