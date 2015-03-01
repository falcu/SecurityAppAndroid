package com.example.guido.securityapp.comparators;

import com.example.guido.securityapp.models.LocalityModel;

import java.util.Comparator;

/**
 * Created by guido on 3/1/15.
 */
public class LocalitiesComparator implements Comparator<LocalityModel> {

    @Override
    public int compare(LocalityModel l1, LocalityModel l2) {
        return l1.getLocality().getName().compareTo(l2.getLocality().getName());
    }
}
