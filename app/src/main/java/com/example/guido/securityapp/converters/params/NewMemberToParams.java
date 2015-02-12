package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.CreateGroupTO;
import com.example.guido.securityapp.models.NewMemberTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guido on 2/9/15.
 */
public class NewMemberToParams extends Converter {
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            NewMemberTO maybeNewMember = (NewMemberTO) objectToConvert;
            JSONObject group_details = new JSONObject();
            String[] emails = new String[]{maybeNewMember.getEmail()};
            JSONArray emailsJson = new JSONArray(emails);
            group_details.put("members_email",(Object)emailsJson);
            group_details.put("id",maybeNewMember.getGroupId());
            return group_details.toString();
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException("I was expecting "+NewMemberTO.class.toString()+" but received "+objectToConvert.getClass().toString());
        }
    }
}
