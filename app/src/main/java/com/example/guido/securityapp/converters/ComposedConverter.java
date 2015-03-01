package com.example.guido.securityapp.converters;

/**
 * Created by guido on 2/23/15.
 */
public class ComposedConverter extends Converter{

    private Converter simpleConverter;
    private ComposedConverter composedConverter = null;

    public ComposedConverter(Converter simpleConverter) {
        this.simpleConverter = simpleConverter;
    }

    @Override
    public Object convert(Object objectToConvert) throws Exception {
        if(composedConverter!=null)
        {
            try
            {
                return simpleConverter.convert(composedConverter.convert(objectToConvert));
            }
            catch (Exception e) //can't converter
            {
                return simpleConverter.convert(objectToConvert);
            }

        }
        else
        {
            return simpleConverter.convert(objectToConvert);
        }
    }

    public void setComposedConverter(ComposedConverter composedConverter) {
        this.composedConverter = composedConverter;
    }
}
