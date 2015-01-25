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
        boolean isValid = false;
        if(composedValidator!=null)
            isValid = composedValidator.isValid(objectToValidate);
        if(!isValid)
            isValid = validator.isValid(objectToValidate);

        return isValid;
    }

    @Override
    public String getError(Object objectToValidate) {
        String error = "";
        if(composedValidator!=null)
            error = composedValidator.getError(objectToValidate);
        if(error.isEmpty())
            error = validator.getError(objectToValidate);

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
