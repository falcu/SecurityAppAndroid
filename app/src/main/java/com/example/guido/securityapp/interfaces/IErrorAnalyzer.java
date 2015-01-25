package com.example.guido.securityapp.interfaces;

/**
 * Created by guido on 1/25/15.
 */
public interface IErrorAnalyzer {

    public void analyze(Object data);
    public boolean didLastDataHaveError();
    public String getLastErrorMessage();

}
