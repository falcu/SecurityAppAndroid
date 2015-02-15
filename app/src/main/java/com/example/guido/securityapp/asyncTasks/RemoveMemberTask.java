package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceOperationMember;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * Created by guido on 2/11/15.
 */
public class RemoveMemberTask extends OperationMemberTask{


    public RemoveMemberTask(NewMemberTO newMemberTO) throws Exception
    {
        super(newMemberTO);
    }

    @Override
    protected BuilderServiceOperationMember.MemberOperation getOperation() {
        return BuilderServiceOperationMember.MemberOperation.REMOVE;
    }

    @Override
    protected String getOwnErrorMessage() {
        return "Unable to remove member, try again later";
    }
}
