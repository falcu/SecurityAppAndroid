package com.example.guido.securityapp.asyncTasks;

/**
 * Created by guido on 1/25/15.
 */
public class TaskResult {
    private boolean isSuccesful = true;
    private Object result = null;
    private String error = null;


    public boolean isSuccesful() {
        return isSuccesful;
    }

    public Object getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public void setResult(Object result) {
        if(result!=null)
        {
            isSuccesful = true;
            this.result = result;
            error = null;
        }
    }

    public void setError(String error) {
        if(error!=null && !error.isEmpty())
        {
            isSuccesful = false;
            this.error = error;
        }
        else
        {
            isSuccesful = true;
            this.error = null;
        }
    }
}
