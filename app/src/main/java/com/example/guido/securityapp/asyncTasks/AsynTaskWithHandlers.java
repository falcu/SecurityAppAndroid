package com.example.guido.securityapp.asyncTasks;

import android.os.AsyncTask;

import com.example.guido.securityapp.interfaces.ITaskHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/1/15.
 */
public abstract class AsynTaskWithHandlers extends AsyncTask<Void, Void, TaskResult> {
    protected List<ITaskHandler> handlers = new ArrayList<ITaskHandler>();

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


}
