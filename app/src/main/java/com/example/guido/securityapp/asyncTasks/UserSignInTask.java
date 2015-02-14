package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceSign;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * Created by guido on 1/24/15.
 */
public class UserSignInTask extends AsynTaskWithHandlers{

    private final String email;
    private final String password;


    public UserSignInTask(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        try {

              UserTO userTransferObject = new UserTO();
              userTransferObject.setEmail(email);
              userTransferObject.setPassword(password);
              ServiceSign serviceSign = BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_IN);
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
}