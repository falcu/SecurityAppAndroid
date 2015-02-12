package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderOperationMemberService;
import com.example.guido.securityapp.models.NewMemberTO;
import com.example.guido.securityapp.services.ServiceOperationToMember;

/**
 * Created by guido on 2/9/15.
 */
public class AddMemberTask extends OperationMemberTask{

    public AddMemberTask(NewMemberTO newMemberTO)
    {
        super(newMemberTO);
    }

    @Override
    protected BuilderOperationMemberService.MemberOperation getOperation() {
        return BuilderOperationMemberService.MemberOperation.ADD;
    }

    @Override
    protected String getOwnErrorMessage() {
        return "Unable to add member, try again later";
    }
}
