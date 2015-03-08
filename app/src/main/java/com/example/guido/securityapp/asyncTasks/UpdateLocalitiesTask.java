package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceLocalities;
import com.example.guido.securityapp.models.TokenTO;
import com.example.guido.securityapp.services.ServiceLocalities;

/**
 * Created by guido on 2/23/15.
 */
public class UpdateLocalitiesTask extends AsynTaskWithHandlers{

    protected TokenTO tokenTO;

    public UpdateLocalitiesTask(TokenTO tokenTO) throws Exception {
        super(tokenTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult taskResult = new TaskResult();

        try {
            ServiceLocalities serviceLocalities = BuilderServiceLocalities.buildServiceLocalities();
            serviceLocalities.updateLocalities(tokenTO);
        } catch (Exception e) {
            taskResult.setError(new TaskError(e.getMessage()));
        }

        return taskResult;
    }

    @Override
    protected Class getTransferObjectType() {
        return TokenTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        tokenTO = (TokenTO) transferObject;
    }
}
