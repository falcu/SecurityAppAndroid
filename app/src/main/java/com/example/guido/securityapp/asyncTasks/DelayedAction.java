package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.commands.Command;

/**
* Created by guido on 3/15/15.
*/
public class DelayedAction implements Runnable
{
   protected long timeToDelayAction; //in milliseconds
   protected Command command;

    public DelayedAction(long timeToDelayAction, Command command)
    {
        this.timeToDelayAction = timeToDelayAction;
        this.command = command;
    }
    @Override
    public void run() {
        try
        {
            Thread.sleep(timeToDelayAction);
            command.execute();
        }
        catch (Exception e){return;}
    }
}
