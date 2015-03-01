package com.example.guido.securityapp.converters;

import com.example.guido.securityapp.helpers.MenuItemData;
import com.example.guido.securityapp.models.Locality;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guido on 3/1/15.
 */
public class LocalitiesClassificationToMenuITemData extends Converter{

    Converter helperConverter;

    public LocalitiesClassificationToMenuITemData()
    {
        helperConverter = new LocalityClassificationToTitle();
    }

    @Override
    public Object convert(Object objectToConvert) throws Exception {

        List<MenuItemData> menuItemsData = new ArrayList<>();
        Locality.LocalityClassification[] classifications = ( Locality.LocalityClassification[] ) objectToConvert;
        int order = 0;
        for(Locality.LocalityClassification c : classifications)
        {
            MenuItemData itemData = new MenuItemData(0,order,order,(String)helperConverter.convert(c),c);
            menuItemsData.add(itemData);
            order++;
        }
        return menuItemsData;
    }
}
