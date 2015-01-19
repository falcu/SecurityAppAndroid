package com.example.guido.securityapp.converter_tests;


import com.example.guido.securityapp.converters.json.HttpUserResponseToJson;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by guido on 1/18/15.
 */
public class TestHttpUserResponseToJson extends TestCase{

    public void testConvert_notAString_RaiseException()
    {
        HttpUserResponseToJson converter = new HttpUserResponseToJson();
        try
        {
            converter.convert(2);
            Assert.fail("Expected to fail");
        }
        catch (IllegalArgumentException e)
        {

        }
        catch (Exception e)
        {
            Assert.fail("Expected different exception");
        }
    }

    public void testConvert_stringButNotUserJson_RaiseException()
    {
        HttpUserResponseToJson converter = new HttpUserResponseToJson();
        String json = "{\"not_user\"{}}";
        try
        {
            converter.convert(json);
            Assert.fail("Expect exception to be raised");
        }
        catch (Exception e)
        {
        }
    }

    public void testConvert_stringWithUserKey_jsonObtained() throws Exception
    {
        HttpUserResponseToJson converter = new HttpUserResponseToJson();
        String json = "{\"user\":{\"name\":\"aName\"}}\n";
        String expected ="{\"name\":\"aName\"}";

        String actual = (String)converter.convert(json);

        Assert.assertEquals(expected,actual);


    }

}
