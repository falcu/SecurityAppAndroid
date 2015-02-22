package com.example.guido.securityapp.commands;

/**
 * Created by guido on 2/19/15.
 */
public abstract class Command {
    public abstract boolean canExecute();
    public void execute()
    {
        if(canExecute())
            executeImplementation();
    }
    protected abstract void executeImplementation();
}
