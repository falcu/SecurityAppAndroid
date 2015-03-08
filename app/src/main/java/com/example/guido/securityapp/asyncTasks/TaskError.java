package com.example.guido.securityapp.asyncTasks;

/**
 * Created by guido on 3/8/15.
 */
public class TaskError {

    private String errorMessage = "";
    private Object errorParam = null;
    private Exception exception = null;

    public TaskError() {
    }

    public TaskError(String errorMessage) {
        this.setErrorMessage(errorMessage);
    }

    public TaskError(String errorMessage, Exception exception) {
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public TaskError(Exception exception) {
        this.exception = exception;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getErrorParam() {
        return errorParam;
    }

    public void setErrorParam(Object errorParam) {
        this.errorParam = errorParam;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
