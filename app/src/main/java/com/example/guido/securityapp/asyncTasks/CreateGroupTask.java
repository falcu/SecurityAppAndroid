package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.services.ServiceCreateGroup;

/**
 * Created by guido on 2/1/15.
 */
public class CreateGroupTask extends AsynTaskWithHandlers{
    private CreateGroupTO createGroupTO;

    public CreateGroupTask(CreateGroupTO createGroupTO)
    {
        this.createGroupTO = createGroupTO;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            ServiceCreateGroup service = BuilderGroupService.buildCreateService();
            service.createGroup(createGroupTO);
            if(service.wasRequestWithError())
            {
                result.setError(service.getLastErrorMessage());
            }
        }
        catch (Exception e)
        {
            result.setError("Unable to create group, try again");
        }
        return result;
    }
}
