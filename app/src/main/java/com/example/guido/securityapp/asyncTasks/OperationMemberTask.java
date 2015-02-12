package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderOperationMemberService;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceOperationToMember;

/**
 * Created by guido on 2/11/15.
 */
public abstract class OperationMemberTask extends AsynTaskWithHandlers{
    protected NewMemberTO newMemberTO;

    public OperationMemberTask(NewMemberTO newMemberTO)
    {
        this.newMemberTO = newMemberTO;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            BuilderOperationMemberService.MemberOperation operationToPerfrom = getOperation();
            ServiceOperationToMember service = BuilderOperationMemberService.build(operationToPerfrom);
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

    protected abstract BuilderOperationMemberService.MemberOperation getOperation();

    protected abstract String getOwnErrorMessage();
}
