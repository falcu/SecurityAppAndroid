package com.example.guido.securityapp.builders;

import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.validators.ComposedValidator;
import com.example.guido.securityapp.validators.EmailValidator;
import com.example.guido.securityapp.validators.EmptyStringValidator;
import com.example.guido.securityapp.validators.LongStringValidator;
import com.example.guido.securityapp.validators.PasswordConfirmationValidator;
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
            case GROUP_NAME:
                return getGroupNameValidator();
            case PASSWORD_CONFIRMATION:
                return getPasswordConfirmationValidator();
        }

        throw new IllegalArgumentException("There is not a validator for that type");
    }

    private IValidate getEmailValidator()
    {
        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();
        EmailValidator emailValidator = new EmailValidator();
        ComposedValidator composedValidator = makeComposedValidatorWith(emptyStringValidator);
        composedValidator.compose(makeComposedValidatorWith(emailValidator));

        return composedValidator;
    }

    private IValidate getPasswordValidator()
    {
        EmptyStringValidator emptyStringValidator = new EmptyStringValidator();
        ShortStringValidator shortStringValidator = new ShortStringValidator(4);
        ComposedValidator composedValidator = makeComposedValidatorWith(emptyStringValidator);
        composedValidator.compose(makeComposedValidatorWith(shortStringValidator));

        return composedValidator;
    }

    private IValidate getNameValidator()
    {
        return new EmptyStringValidator();
    }

    private IValidate getGroupNameValidator()
    {
        ComposedValidator composedValidator = makeComposedValidatorWith(new EmptyStringValidator());
        ComposedValidator composedValidator2 = makeComposedValidatorWith(new ShortStringValidator(3));
        composedValidator2.compose(makeComposedValidatorWith(new LongStringValidator(20)));
        composedValidator.compose(composedValidator2);

        return composedValidator;
    }

    private IValidate getPasswordConfirmationValidator()
    {
        return new PasswordConfirmationValidator();
    }

    private ComposedValidator makeComposedValidatorWith(IValidate validator)
    {
        ComposedValidator composedValidator = new ComposedValidator();
        composedValidator.setValidator(validator);
        return composedValidator;
    }


    public enum ValidatorType
    {
        EMAIL,
        PASSWORD,
        PASSWORD_CONFIRMATION,
        NAME,
        GROUP_NAME
    }
}


