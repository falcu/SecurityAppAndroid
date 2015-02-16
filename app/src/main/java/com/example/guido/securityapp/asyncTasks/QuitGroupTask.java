package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.models.QuitGroupTO;
import com.example.guido.securityapp.services.ServiceQuitGroup;

/**
 * Created by guido on 2/16/15.
 */
public class QuitGroupTask extends AsynTaskWithHandlers {
    protected QuitGroupTO quitGroupTO;

    public QuitGroupTask(QuitGroupTO quitGroupTO) throws Exception
    {
        super(quitGroupTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult taskResult = new TaskResult();
        try
        {
            ServiceQuitGroup serviceQuitGroup = BuilderServiceGroup.builderQuitGroupService();
            serviceQuitGroup.quitGroup(quitGroupTO);
            if(serviceQuitGroup.wasRequestWithError())
            {
                taskResult.setError(serviceQuitGroup.getLastErrorMessage());
            }
            else if(serviceQuitGroup.wasRequestWithMessage())
            {
                taskResult.setResult((String)serviceQuitGroup.getLastMessage());
            }
        }
        catch (Exception e)
        {
            taskResult.setError(e.getMessage());
        }

        return taskResult;
    }

    @Override
    protected Class getTransferObjectType() {
        return QuitGroupTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        quitGroupTO = (QuitGroupTO) transferObject;
    }
}
