package com.example.guido.securityapp.interfaces;

import android.app.Activity;

import com.example.guido.securityapp.asyncTasks.TaskResult;

/**
 * Created by guido on 1/24/15.
 */
public interface ITaskHandler {
    public void onPreExecute();
    public void onPostExecute(TaskResult taskResult);
    public void onCancelled();
}
