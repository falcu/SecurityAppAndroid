package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceOperationMember;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * Created by guido on 2/9/15.
 */
public class AddMemberTask extends OperationMemberTask{

    public AddMemberTask(NewMemberTO newMemberTO)
    {
        super(newMemberTO);
    }

    @Override
    protected BuilderServiceOperationMember.MemberOperation getOperation() {
        return BuilderServiceOperationMember.MemberOperation.ADD;
    }

    @Override
    protected String getOwnErrorMessage() {
        return "Unable to add member, try again later";
    }
}
