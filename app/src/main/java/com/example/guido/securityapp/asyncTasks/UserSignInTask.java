package com.example.guido.securityapp.asyncTasks;

import android.os.AsyncTask;

import com.example.guido.securityapp.builders.BuilderRegisterIdService;
import com.example.guido.securityapp.builders.BuilderSignService;
import com.example.guido.securityapp.builders.SignOptions;
import com.example.guido.securityapp.interfaces.ITaskHandler;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceRegisterId;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * Created by guido on 1/24/15.
 */
public class UserSignInTask extends AsyncTask<Void, Void, TaskResult> {

    private final String email;
    private final String password;
    private ITaskHandler handler;


    public UserSignInTask(String email, String password, ITaskHandler handler) {
        this.email = email;
        this.password = password;
        this.handler = handler;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        try {

              UserTO userTransferObject = new UserTO();
              userTransferObject.setEmail(email);
              userTransferObject.setPassword(password);
              ServiceSign serviceSign = BuilderSignService.buildServiceSign(SignOptions.SIGN_IN);
              serviceSign.sign(userTransferObject);
              if(serviceSign.wasRequestWithError())
              {
                  result.setError(serviceSign.getLastErrorMessage());
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
