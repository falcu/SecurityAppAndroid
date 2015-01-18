package com.example.guido.securityapp.converters;

import java.util.Objects;

/**
 * Created by guido on 1/18/15.
 */
public abstract class Converter {

    protected Object toConvert;

    public abstract Object convert(Object objectToConvert) throws Exception;
    public void setObject(Object toConvert)
    {
        this.toConvert = toConvert;
    }

    public Object convert() throws Exception
    {
        if(toConvert==null)
            throw new IllegalArgumentException("The Object to convert has to be set");

        return this.convert(this.toConvert);
    }
}
