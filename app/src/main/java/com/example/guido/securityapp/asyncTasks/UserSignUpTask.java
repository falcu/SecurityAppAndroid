package com.example.guido.securityapp.asyncTasks;

import android.app.Activity;

import com.example.guido.securityapp.builders.services.BuilderServiceRegisterId;
import com.example.guido.securityapp.builders.services.BuilderServiceSign;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceSign;


/**
 * Created by guido on 1/24/15.
 */
public class UserSignUpTask extends AsynTaskWithHandlers{
    private final String name;
    private final String email;
    private final String password;
    private Activity activity;


    public UserSignUpTask(String name, String email, String password,Activity activity) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        try {
            ServiceRegisterId serviceRegisterId = BuilderServiceRegisterId.buildRegisterIdService();
            serviceRegisterId.setRegistrationIdWithErrorDialog(this.activity);
            String regId = serviceRegisterId.getRegistrationId();
            if(!regId.isEmpty())
            {
                UserTO userTransferObject = new UserTO();
                userTransferObject.setName(name);
                userTransferObject.setEmail(email);
                userTransferObject.setPassword(password);
                userTransferObject.setRegistrationId(regId);
                ServiceSign serviceSign = BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_UP);
                serviceSign.sign(userTransferObject);
                if(serviceSign.wasRequestWithError())
                {
                    result.setError(serviceSign.getLastErrorMessage());
                }

            }
        } catch (Exception e) {
            result.setError(e.getMessage());
        }

        return result;
    }

}