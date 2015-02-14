package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 2/8/15.
 */
public interface IFragmentDelayedButton {
    public void setDelayedAction(ICommand action);
    public void setImage(int resourceId);
    public void setDelayTime(int milliseconds);

}
