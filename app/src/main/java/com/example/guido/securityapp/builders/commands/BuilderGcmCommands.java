package com.example.guido.securityapp.builders.commands;

import android.content.Intent;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.commands.Command;
import com.example.guido.securityapp.commands.GroupChangedGcmCommand;
import com.example.guido.securityapp.converters.json.GcmGroupToJson;
import com.example.guido.securityapp.converters.json.HttpOperationMemberResponseToJson;
import com.example.guido.securityapp.converters.json.JsonToObject;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IDataStore;
import com.example.guido.securityapp.models.Group;
import com.example.guido.securityapp.services.ServiceStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guido on 2/20/15.
 */
public class BuilderGcmCommands {

    protected List<Command> commands;

    public BuilderGcmCommands()
    {
        commands = new ArrayList<>();
    }

    public Iterator<Command> getCommands(Intent intent)
    {
        ininitializeIfNeeded(intent);
        return commands.iterator();
    }

    protected void ininitializeIfNeeded(Intent intent)
    {
        if(commands.isEmpty())
        {
            IDataStore store = new ServiceStore(MyApplication.getContext().getString(R.string.group_store_key), new JsonToObject(Group.class),new GcmGroupToJson());
            GroupChangedGcmCommand groupChangedGcmCommand = new GroupChangedGcmCommand(intent, FactoryEventAggregator.getInstance(),store);
            commands.add(groupChangedGcmCommand);
        }
    }
}
