package com.example.guido.securityapp.gcm;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.converters.Converter;
import com.example.guido.securityapp.models.Group;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/21/15.
 */
public class GcmParser {

    protected Intent intent;
    protected String message = null;
    protected String type = null;

    public GcmParser(Intent intent)
    {
        this.intent = intent;
    }

    public String getMessage()
    {
        if(message == null)
        {
            message = intent.getStringExtra("message");
        }
        return message;
    }

    public String getType()
    {
        if(type == null)
        {
            type = intent.getStringExtra("type");
        }

        return type;
    }

    public ResponseType getResponseTypeObject()
    {
        if(intent.getStringExtra("group_info")!=null)
        {
            return ResponseType.GROUP;
        }
        return null;
    }

    public enum ResponseType
    {
        GROUP
    }

}
