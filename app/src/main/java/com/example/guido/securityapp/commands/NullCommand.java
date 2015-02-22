package com.example.guido.securityapp.commands;

/**
 * Created by guido on 2/16/15.
 */
public class NullCommand extends Command {

    @Override
    protected void executeImplementation() {
        //nothing to do
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
