package com.example.guido.securityapp.asyncTasks;

/**
 * Created by guido on 1/25/15.
 */
public class TaskResult {
    private boolean isSuccesful = true;
    private Object result = null;
    private TaskError error = null;
    private String identifier = "";


    public boolean isSuccesful() {
        return isSuccesful;
    }

    public Object getResult() {
        return result;
    }

    public TaskError getError() {
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

    public void setError(TaskError error) {
        if(error!=null)
        {
            isSuccesful = false;
            this.error = error;
        }
        else
        {
            isSuccesful = true;
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
