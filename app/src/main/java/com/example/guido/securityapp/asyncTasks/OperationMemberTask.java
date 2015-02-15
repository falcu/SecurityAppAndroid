package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceOperationMember;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceOperationToMember;

/**
 * Created by guido on 2/11/15.
 */
public abstract class OperationMemberTask extends AsynTaskWithHandlers{
    protected NewMemberTO newMemberTO;

    public OperationMemberTask(Object newMemberTO) throws Exception
    {
        super(newMemberTO);
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
                result.setError(service.getLastErrorMessage());
            }
        }
        catch (Exception e)
        {
            result.setError(getOwnErrorMessage());
        }
        return result;
    }

    protected abstract BuilderServiceOperationMember.MemberOperation getOperation();

    protected abstract String getOwnErrorMessage();
}
