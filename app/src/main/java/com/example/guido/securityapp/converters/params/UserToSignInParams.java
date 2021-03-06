package com.example.guido.securityapp.converters.params;

import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.exceptions.IncompleteUserException;
import com.example.guido.securityapp.models.UserTO;

import org.json.JSONObject;

/**
 * Created by guido on 1/17/15.
 */
public class UserToSignInParams extends Converter {

    @Override
    public Object convert(Object toConvert) throws Exception {

        UserTO maybeUser = (UserTO) toConvert;

        if(maybeUser==null)
            throw new IllegalArgumentException("I'm expecting a UserTO");

        if (maybeUser.getEmail().isEmpty() || maybeUser.getPassword().isEmpty())
            throw new IncompleteUserException("Key information is missing in the userTO");

        JSONObject user_details = new JSONObject();
        JSONObject userJSON = new JSONObject();
        user_details.put("email", maybeUser.getEmail());
        user_details.put("password", maybeUser.getPassword());
        user_details.put("password_confirmation", maybeUser.getPasswordConfirmation());
        JSONObject deviceJSON = new JSONObject();
        if(maybeUser.getRegistrationId()!=null) {
            deviceJSON.put("registration_id", maybeUser.getRegistrationId());
            user_details.put("device", deviceJSON);
        }
        userJSON.put("user", user_details);

        return userJSON.toString();
    }
}
