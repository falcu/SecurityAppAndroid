package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;

/**
 * Created by guido on 2/8/15.
 */
public class StringEmptyConverter extends Converter{
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        return "";
    }
}
