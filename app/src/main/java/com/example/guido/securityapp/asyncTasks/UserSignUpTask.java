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
    protected UserTO userTO;
    private Activity activity;


    public UserSignUpTask(UserTO userTO,Activity activity) throws Exception{
        super(userTO);
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
                this.userTO.setRegistrationId(regId);
                ServiceSign serviceSign = BuilderServiceSign.buildServiceSign(BuilderServiceSign.SignOptions.SIGN_UP);
                serviceSign.sign(this.userTO);
                if(serviceSign.wasRequestWithError())
                {
                    result.setError(new TaskError(serviceSign.getLastErrorMessage()));
                }

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