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
    private Locality currentLocality;

    public SetLocalityClassificationTask(LocalityClassificationTO localityClassification,Locality currentLocality)throws Exception
    {
        super(localityClassification);
        this.currentLocality = currentLocality;
    }

    @Override
    protected TaskResult doInBackground(Void... params) {
        TaskResult taskResult = new TaskResult();

        ServiceSetLocalityClassification service = BuilderServiceLocalities.buildServiceSetLocalityClassification();
        try {
            Locality l = service.updateLocalityClassification(localityClassificationTO);
            if(service.wasRequestWithError())
            {
                TaskError error = new TaskError(service.getLastErrorMessage());
                error.setErrorParam(currentLocality);
                taskResult.setError(error);
            }
            else
            {
                taskResult.setResult(l);
            }
        } catch (Exception e) {
            TaskError error = new TaskError(e);
            error.setErrorParam(currentLocality);
            taskResult.setError(error);
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
