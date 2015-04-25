package com.example.guido.securityapp.validators;

import com.example.guido.securityapp.interfaces.IValidate;

/**
 * Created by guido on 4/25/15.
 */
public class PasswordConfirmationValidator implements IValidate{

    private EmptyStringValidator emptyStringValidator;
    private EqualsValidator equalsValidator;

    public PasswordConfirmationValidator()
    {
        emptyStringValidator = new EmptyStringValidator();
        equalsValidator = new EqualsValidator("It must be equal to the password");
    }

    @Override
    public boolean isValid(Object objectToValidate) {
        validateArgument(objectToValidate);

        PasswordPair pair = (PasswordPair) objectToValidate;
        return emptyStringValidator.isValid(pair.getReentryPassword()) && equalsValidator.isValid(pair);

    }

    @Override
    public String getError(Object objectToValidate) {
        validateArgument(objectToValidate);

        PasswordPair pair = (PasswordPair) objectToValidate;
        String emptyError = emptyStringValidator.getError(pair.getReentryPassword());
        if(emptyError.isEmpty())
        {
            return equalsValidator.getError(new ObjectPair(pair.getPassword(),pair.getReentryPassword()));
        }
        else
            return emptyError;
    }

    private void validateArgument(Object objectToValidate)
    {
        if(objectToValidate.getClass()!=PasswordPair.class)
            throw new IllegalArgumentException("I was expecting PasswordPair");
    }

}
