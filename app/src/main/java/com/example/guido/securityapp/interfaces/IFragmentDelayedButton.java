package com.example.guido.securityapp.interfaces;

import com.example.guido.securityapp.commands.Command;

/**
 * Created by guido on 2/8/15.
 */
public interface IFragmentDelayedButton {
    public void setDelayedAction(Command action);
    public void setImage(int resourceId);
    public void setDelayTime(int milliseconds);

}
