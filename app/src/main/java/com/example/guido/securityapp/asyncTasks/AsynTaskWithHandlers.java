package com.example.guido.securityapp.asyncTasks;

import android.os.AsyncTask;

import com.example.guido.securityapp.interfaces.ITaskHandler;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/1/15.
 */
public abstract class AsynTaskWithHandlers extends AsyncTask<Void, Void, TaskResult> {
    protected List<ITaskHandler> handlers = new ArrayList<ITaskHandler>();
    protected String resultIdentifier = "";

    public AsynTaskWithHandlers(Object transferObject) throws Exception
    {
        setTransferObject(transferObject);
    }

    public AsynTaskWithHandlers(){}

    public void setResultIdentifier(String resultIdentifier)
    {
        this.resultIdentifier = resultIdentifier;
    }

    @Override
    protected abstract TaskResult doInBackground(Void... params);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         for(ITaskHandler handler : handlers){
             handler.onPreExecute();
         }
    }

    @Override
    protected void onPostExecute(TaskResult taskResult) {
        taskResult.setIdentifier(resultIdentifier);
        super.onPostExecute(taskResult);
        for(ITaskHandler handler : handlers){
            handler.onPostExecute(taskResult);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        for(ITaskHandler handler : handlers){
            handler.onCancelled();
        }
    }

    public void addHandler(ITaskHandler handler){handlers.add(handler);}

    public void removeHandler(ITaskHandler handler){handlers.remove(handler);}

    protected abstract Class getTransferObjectType();

    protected void setTransferObject(Object transferObject) throws InvalidClassException
    {
        if(!transferObject.getClass().equals(getTransferObjectType()))
        {
            throw new InvalidClassException("I was expecting a "+getTransferObjectType().toString()+", but got "+transferObject.getClass().toString());
        }
        setSpecificObject(transferObject);
    }

    protected abstract void setSpecificObject(Object transferObject);


}
