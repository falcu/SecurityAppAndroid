package com.example.guido.securityapp.builders.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.example.guido.securityapp.adapters.LocalitiesAdapter;
import com.example.guido.securityapp.converters.LocalityClassificationToTitle;
import com.example.guido.securityapp.factories.FactoryEventAggregator;
import com.example.guido.securityapp.interfaces.IBuildAdapter;
import com.example.guido.securityapp.models.Locality;
import com.example.guido.securityapp.models.LocalityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 2/23/15.
 */
public class BuilderLocalitiesAdapter implements IBuildAdapter{

    protected Activity activity;
    protected List<Locality> localities;

    public BuilderLocalitiesAdapter(Activity activity,List<Locality> localities) {
        this.activity = activity;
        this.localities = localities;
    }

    @Override
    public BaseAdapter buildAdapter() throws Exception {
        return new LocalitiesAdapter(activity,convertToModel(localities), FactoryEventAggregator.getInstance(), new LocalityClassificationToTitle());
    }

    private List<LocalityModel> convertToModel(List<Locality> localities)
    {
        List<LocalityModel> localityModels = new ArrayList<>();
        for(Locality l : localities)
            localityModels.add(new LocalityModel(l,false));

        return localityModels;
    }
}
