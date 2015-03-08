package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.services.BuilderServiceSign;
import com.example.guido.securityapp.models.UserTO;
import com.example.guido.securityapp.services.ServiceSign;

/**
 * Created by guido on 1/24/15.
 */
public class UserSignInTask extends AsynTaskWithHandlers{

    protected UserTO userTO;


    public UserSignInTask(UserTO userTO) throws Exception{
        super(userTO);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult result = new TaskResult();

        try {
              ServiceSign serviceSign = BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_IN);
              serviceSign.sign(this.userTO);
              if(serviceSign.wasRequestWithError())
              {
                  result.setError(new TaskError(serviceSign.getLastErrorMessage()));
              }


        } catch (Exception e) {
            result.setError(new TaskError(e));
        }

        return result;
    }

    @Override
    protected Class getTransferObjectType() {
        return UserTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        this.userTO = (UserTO) transferObject;
    }
}
