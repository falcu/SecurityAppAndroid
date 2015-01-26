package com.example.guido.securityapp.asyncTasks;

import android.os.AsyncTask;

import com.example.guido.securityapp.builders.BuilderRegisterIdService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceSign;


/**
 * Created by guido on 1/24/15.
 */
public class UserSignUpTask extends AsyncTask<Void, Void, TaskResult>{
    private final String name;
    private final String email;
    private final String password;
    private ITaskHandler handler;


    public UserSignUpTask(String name, String email, String password, ITaskHandler handler) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.handler = handler;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        try {
            ServiceRegisterId serviceRegisterId = BuilderRegisterIdService.buildRegisterIdService();
            serviceRegisterId.setRegistrationIdWithErrorDialog(this.handler.getActivityInstance());
            String regId = serviceRegisterId.getRegistrationId();
            if(!regId.isEmpty())
            {
                UserTO userTransferObject = new UserTO();
                userTransferObject.setName(name);
                userTransferObject.setEmail(email);
                userTransferObject.setPassword(password);
                userTransferObject.setRegistrationId(regId);
                ServiceSign serviceSign = BuilderSignService.buildServiceSign(BuilderSignService.SignOptions.SIGN_UP);
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

    @Override
    protected void onPostExecute(TaskResult success) {
        handler.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        handler.onCancelled();
    }

}