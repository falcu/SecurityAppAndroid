package com.example.guido.securityapp.builders.http_requests;

import com.example.guido.securityapp.builders.http_requests.BuilderAddMemberRequest;

/**
 * Created by guido on 2/11/15.
 */
public class BuilderRemoveMemberRequest extends BuilderAddMemberRequest {

    @Override
    protected String getSpecificUri() {
        return "/api/groups/remove_members";
    }
}
