package com.example.guido.securityapp.interfaces;

import com.example.guido.securityapp.fragments.Option;
import com.example.guido.securityapp.fragments.OptionsFragment;

import java.util.List;

/**
 * Created by guido on 2/8/15.
 */
public interface IFragmentOptions {
    public void addOption(Option option);
    public void setOptions(List<Option> options);
}
