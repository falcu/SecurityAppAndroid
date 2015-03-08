package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceGroup;
import com.example.guido.securityapp.builders.services.BuilderServiceOperationMember;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IEventAggregator;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceOperationToMember;

/**
 * Created by guido on 2/11/15.
 */
public abstract class OperationMemberTask extends AsynTaskWithHandlers{
    protected NewMemberTO newMemberTO;
    protected IEventAggregator eventAggregator;


    public OperationMemberTask(Object newMemberTO) throws Exception
    {
        super(newMemberTO);
        eventAggregator = FactoryEventAggregator.getInstance();
    }

    @Override
    protected Class getTransferObjectType() {
        return NewMemberTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        newMemberTO = (NewMemberTO) transferObject;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            BuilderServiceOperationMember.MemberOperation operationToPerfrom = getOperation();
            ServiceOperationToMember service = BuilderServiceOperationMember.build(operationToPerfrom);
            service.doOperation(newMemberTO);
            if(service.wasRequestWithError())
            {
                result.setError(new TaskError(service.getLastErrorMessage()));
            }
        }
        catch (Exception e)
        {
            result.setError(new TaskError(getOwnErrorMessage()));
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
                //TODO HANDLE EXCEPTION
            }
        }
        super.onPostExecute(taskResult);
    }

    protected abstract BuilderServiceOperationMember.MemberOperation getOperation();

    protected abstract String getOwnErrorMessage();
}
