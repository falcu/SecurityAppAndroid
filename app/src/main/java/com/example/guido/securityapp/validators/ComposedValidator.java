package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 1/24/15.
 */
public class ComposedValidator implements IValidate {

    private IValidate validator = new DefaultValidator();
    private ComposedValidator composedValidator;

    @Override
    public boolean isValid(Object objectToValidate) {
        if(composedValidator==null)
            return validator.isValid(objectToValidate);
        else
            return validator.isValid(objectToValidate) && composedValidator.isValid(objectToValidate);
    }

    @Override
    public String getError(Object objectToValidate) {
        String error = validator.getError(objectToValidate);
        if((error==null ||  error.isEmpty()) && composedValidator!=null)
        {
            error = composedValidator.getError(objectToValidate);
        }

        return error;
    }

    public void setValidator(IValidate validator)
    {
        this.validator = validator;
    }

    public void compose(ComposedValidator composedValidator)
    {
        this.composedValidator = composedValidator;
    }
}
