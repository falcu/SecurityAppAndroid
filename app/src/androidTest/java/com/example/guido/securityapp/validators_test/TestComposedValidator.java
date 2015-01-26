package com.example.guido.securityapp.validators_test;

import com.example.guido.securityapp.interfaces.IValidate;
import com.example.guido.securityapp.validators.ComposedValidator;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by guido on 1/25/15.
 */
public class TestComposedValidator extends TestCase{

    public void testIsValid_onlyOneValidValidator_returnsTrue()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubValidValidator());

        boolean result = composedValidator.isValid("toValidate");

        Assert.assertTrue(result);
    }

    public void testIsValid_onlyOneInvalidValidator_returnsFalse()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubInvalidValidator());

        boolean result = composedValidator.isValid("toValidate");

        Assert.assertFalse(result);
    }

    public void testIsValid_twoValidValidators_returnsTrue()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubValidValidator());
        ComposedValidator composedValidator2 = makeValidatorWith(new StubValidValidator());
        composedValidator.compose(composedValidator2);

        boolean result = composedValidator.isValid("toValidate");

        Assert.assertTrue(result);
    }

    public void testIsValid_oneValidOneInvalid_returnsFalse()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubValidValidator());
        ComposedValidator composedValidator2 = makeValidatorWith(new StubInvalidValidator());
        composedValidator.compose(composedValidator2);

        boolean result = composedValidator.isValid("toValidate");

        Assert.assertFalse(result);
    }

    public void testIsValid_twoInvalidValidators_returnsFalse()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubInvalidValidator());
        ComposedValidator composedValidator2 = makeValidatorWith(new StubInvalidValidator());
        composedValidator.compose(composedValidator2);

        boolean result = composedValidator.isValid("toValidate");

        Assert.assertFalse(result);
    }

    public void testGetError_onlyOneValidValidator_returnsEmpty()
    {
        ComposedValidator composedValidator = makeValidatorWith(new StubValidValidator());

        String result = composedValidator.getError("toValidate");

        Assert.assertEquals("",result);
    }

    public void testGetError_onlyOneInvalidValidator_returnsTheError()
    {
        String expected = "error1";
        StubInvalidValidator invalidValidator = new StubInvalidValidator();
        invalidValidator.setCustomError(expected);
        ComposedValidator composedValidator = makeValidatorWith(invalidValidator);

        String actual = composedValidator.getError("toValidate");

        Assert.assertEquals(expected,actual);
    }

    public void testGetError_invalidWithValid_returnsTheError()
    {
        String expected = "error1";
        StubInvalidValidator invalidValidator = new StubInvalidValidator();
        invalidValidator.setCustomError(expected);
        ComposedValidator composedValidator = makeValidatorWith(invalidValidator);
        composedValidator.compose(makeValidatorWith(new StubValidValidator()));

        String actual = composedValidator.getError("toValidate");

        Assert.assertEquals(expected,actual);
    }

    public void testGetError_invalidWithInvalid_returnsFirstError()
    {
        String expected = "error1";
        StubInvalidValidator invalidValidator = new StubInvalidValidator();
        invalidValidator.setCustomError(expected);
        ComposedValidator composedValidator = makeValidatorWith(invalidValidator);
        StubInvalidValidator invalidValidator2 = new StubInvalidValidator();
        invalidValidator2.setCustomError("error2");
        composedValidator.compose(makeValidatorWith(invalidValidator2));

        String actual = composedValidator.getError("toValidate");

        Assert.assertEquals(expected,actual);
    }

    private ComposedValidator makeValidatorWith(IValidate validator)
    {
        ComposedValidator composedValidator = new ComposedValidator();
        composedValidator.setValidator(validator);
        return composedValidator;
    }

    public class StubInvalidValidator implements IValidate
    {
        private String customError = null;
        @Override
        public boolean isValid(Object objectToValidate) {
            return false;
        }

        @Override
        public String getError(Object objectToValidate) {
            if(customError==null)
                return "my error";
            return customError;
        }

        public void setCustomError(String customError)
        {
            this.customError = customError;
        }
    }

    public class StubValidValidator implements IValidate{

        @Override
        public boolean isValid(Object objectToValidate) {
            return true;
        }

        @Override
        public String getError(Object objectToValidate) {
            return "";
        }
    }
}
