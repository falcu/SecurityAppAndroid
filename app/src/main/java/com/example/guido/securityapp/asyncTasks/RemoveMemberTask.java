package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.BuilderOperationMemberService;
import com.example.guido.securityapp.models.NewMemberTO;

/**
 * Created by guido on 2/11/15.
 */
public class RemoveMemberTask extends OperationMemberTask{


    public RemoveMemberTask(NewMemberTO newMemberTO)
    {
        super(newMemberTO);
    }

    @Override
    protected BuilderOperationMemberService.MemberOperation getOperation() {
        return BuilderOperationMemberService.MemberOperation.REMOVE;
    }

    @Override
    protected String getOwnErrorMessage() {
        return "Unable to remove member, try again later";
    }
}
