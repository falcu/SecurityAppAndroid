package com.example.guido.securityapp.asyncTasks;

/**
 * Created by guido on 2/26/15.
 */
public class DummyWaitTask extends AsynTaskWithHandlers {
    @Override
    protected TaskResult doInBackground(Void... params) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TaskResult();
    }

    @Override
    protected Class getTransferObjectType() {
        return null;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {

    }
}
