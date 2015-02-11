package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderAddMemberService;
import com.example.guido.securityapp.builders.BuilderGroupService;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceAddMember;
import com.example.guido.securityapp.services.ServiceCreateGroup;

/**
 * Created by guido on 2/9/15.
 */
public class AddMemberTask extends AsynTaskWithHandlers{
    private NewMemberTO newMemberTO;

    public AddMemberTask(NewMemberTO newMemberTO)
    {
        this.newMemberTO = newMemberTO;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();
        try
        {
            ServiceAddMember service = BuilderAddMemberService.build();
            service.addMember(newMemberTO);
            if(service.wasRequestWithError())
            {
                result.setError(service.getLastErrorMessage());
            }
        }
        catch (Exception e)
        {
            result.setError("Unable to add member, try again later");
        }
        return result;
    }
}
