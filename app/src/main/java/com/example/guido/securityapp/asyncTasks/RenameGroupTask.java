package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceUserToken;
import com.example.guido.securityapp.exceptions.UnableToLoadGroupException;
import com.example.guido.securityapp.exceptions.UnableToLoadTokenException;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.RenameGroupTO;
import com.example.guido.securityapp.services.ServiceRenameGroup;

/**
 * Created by guido on 2/15/15.
 */
public class RenameGroupTask extends AsynTaskWithHandlers{
    protected RenameGroupTO renameGroupTO;
    protected IEventAggregator eventAggregator;

    public RenameGroupTask(RenameGroupTO renameGroupTO) throws Exception{
        super(renameGroupTO);
        eventAggregator = FactoryEventAggregator.getInstance();
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
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
    protected void onPostExecute(TaskResult taskResult) {
        if(taskResult.isSuccesful())
        {
            try
            {
                Group group = BuilderServiceGroup.buildGroupInformationService().getGroup();
                eventAggregator.Publish(MyApplication.getContext().getResources().getString(R.string.group_updated_event),group);

            }
            catch (Exception e)
            {
              e.getMessage();
            }
        }

        super.onPostExecute(taskResult);
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
