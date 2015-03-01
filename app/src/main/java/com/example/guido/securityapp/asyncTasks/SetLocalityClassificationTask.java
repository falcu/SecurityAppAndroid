package com.example.guido.securityapp.asyncTasks;

import com.example.guido.securityapp.builders.adapters.BuilderLocalitiesAdapter;
import com.example.guido.securityapp.builders.services.BuilderServiceLocalities;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityClassificationTO;
import com.example.guido.securityapp.services.ServiceSetLocalityClassification;

/**
 * Created by guido on 3/1/15.
 */
public class SetLocalityClassificationTask extends AsynTaskWithHandlers {
    private LocalityClassificationTO localityClassificationTO;

    public SetLocalityClassificationTask(LocalityClassificationTO localityClassification)throws Exception
    {
        super(localityClassification);
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult taskResult = new TaskResult();

        ServiceSetLocalityClassification service = BuilderServiceLocalities.buildServiceSetLocalityClassification();
        try {
            Locality l = service.updateLocalityClassification(localityClassificationTO);
            if(service.wasRequestWithError())
            {
                taskResult.setError(service.getLastErrorMessage());
            }
            else
            {
                taskResult.setResult(l);
            }
        } catch (Exception e) {
            taskResult.setException(e);
        }

        return taskResult;
    }

    @Override
    protected Class getTransferObjectType() {
        return LocalityClassificationTO.class;
    }

    @Override
    protected void setSpecificObject(Object transferObject) {
        localityClassificationTO = (LocalityClassificationTO)transferObject;
    }
}
