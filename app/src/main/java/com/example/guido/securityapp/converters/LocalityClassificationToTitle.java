package com.example.guido.securityapp.converters;

import com.example.guido.securityapp.models.Locality;

/**
 * Created by guido on 3/1/15.
 */
public class LocalityClassificationToTitle extends Converter{
    @Override
    public Object convert(Object objectToConvert) throws Exception {
        try
        {
            Locality.LocalityClassification maybeClassification = (Locality.LocalityClassification) objectToConvert;

            switch (maybeClassification)
            {
                case UNCLASSIFIED:
                    return "Let us decide";
                case SECURE:
                    return "Secure";
                case INSECURE:
                    return "Insecure";
            }
            return null;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("I was expecting a "+ Locality.LocalityClassification.class+ " but received "+objectToConvert.getClass());
        }

    }
}
