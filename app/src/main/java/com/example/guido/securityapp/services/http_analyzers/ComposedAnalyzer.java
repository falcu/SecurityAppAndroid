package com.example.guido.securityapp.services.http_analyzers;

import com.example.guido.securityapp.interfaces.IMessageAnalyzer;

/**
 * Created by guido on 3/8/15.
 */
public class ComposedAnalyzer implements IMessageAnalyzer {

    protected IMessageAnalyzer simpleAnalyzer = null;
    private ComposedAnalyzer composedAnalyzer = null;

    public ComposedAnalyzer(IMessageAnalyzer simpleAnalyzer) {
        this.simpleAnalyzer = simpleAnalyzer;
    }

    @Override
    public void analyze(Object data) {
        simpleAnalyzer.analyze(data);
        if(getComposedAnalyzer() !=null)
        {
            getComposedAnalyzer().analyze(data);
        }
    }

    @Override
    public boolean didLastDataHaveMessage() {
        boolean composedValue = false;
        if(composedAnalyzer!=null)
        {
            composedValue = composedAnalyzer.didLastDataHaveMessage();
        }
        return simpleAnalyzer.didLastDataHaveMessage() || composedValue;
    }
    public String getLastMessage() {
        String composedMessage = null;
        if(composedAnalyzer!=null)
        {
          composedMessage = getComposedAnalyzer().getLastMessage();
        }
        if(composedMessage==null ||composedMessage.isEmpty() )
        {
            return simpleAnalyzer.getLastMessage();
        }
        return composedMessage;
    }

    public ComposedAnalyzer getComposedAnalyzer() {
        return composedAnalyzer;
    }

    public void setComposedAnalyzer(ComposedAnalyzer composedAnalyzer) {
        this.composedAnalyzer = composedAnalyzer;
    }
}
