package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.validators.ComposedValidator;
import com.example.guido.securityapp.validators.EmailValidator;
import com.example.guido.securityapp.validators.EmptyStringValidator;
import com.example.guido.securityapp.validators.ShortStringValidator;

/**
 * Created by guido on 1/24/15.
 */
public class BuilderValidator {

    public IValidate buildValidator(ValidatorType type)
    {
        switch (type)
        {
            case EMAIL:
                return getEmailValidator();
            case PASSWORD:
                return getPasswordValidator();
            case NAME:
                return getNameValidator();
        }

        throw new IllegalArgumentException("There is not a validator for that type");
    }

    private IValidate getEmailValidator()
    {
        ComposedValidator composedValidator1 = new ComposedValidator();
        ComposedValidator composedValidator2 = new ComposedValidator();
        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();
        EmailValidator emailValidator = new EmailValidator();
        composedValidator2.setValidator(emptyStringValidator);
        composedValidator1.setValidator(emailValidator);
        composedValidator1.compose(composedValidator2);

        return composedValidator1;
    }

    private IValidate getPasswordValidator()
    {
        ComposedValidator composedValidator1 = new ComposedValidator();
        ComposedValidator composedValidator2 = new ComposedValidator();
        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();
        ShortStringValidator shortStringValidator = new ShortStringValidator(4);
        composedValidator2.setValidator(emptyStringValidator);
        composedValidator1.setValidator(shortStringValidator);
        composedValidator1.compose(composedValidator2);

        return composedValidator1;
    }

    private IValidate getNameValidator()
    {
        return new EmptyStringValidator();
    }


    public enum ValidatorType
    {
        EMAIL,
        PASSWORD,
        NAME
    }
}


