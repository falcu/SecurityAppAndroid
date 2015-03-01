package com.example.guido.securityapp.converters.json;

import com.example.guido.securityapp.converters.Converter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class ArrayToList extends Converter {
    @Override
    public Object convert(Object objectToConvert)  {

        Object[] array = (Object[]) objectToConvert;
        return Arrays.asList(array);
    }

}
