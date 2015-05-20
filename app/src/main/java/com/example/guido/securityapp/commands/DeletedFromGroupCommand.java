package com.example.guido.securityapp.commands;

import com.example.guido.securityapp.R;
import com.example.guido.securityapp.activities.MyApplication;
import com.example.guido.securityapp.builders.services.BuilderServiceDeleteAll;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.models.GroupDeleted;

/**
 * Created by guido on 3/15/15.
 */
public class DeletedFromGroupCommand extends Command{
    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    protected void executeImplementation() {
        BuilderServiceDeleteAll.buildDeletedFromGroup().deleteAll();
        FactoryEventAggregator.getInstance().publish(MyApplication.getContext().getResources().getString(R.string.DELETED_FROM_GROUP), new GroupDeleted());
    }
}
