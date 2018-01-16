package com.cityfilter.utils;

import com.cityfilter.network.models.City;

/**
 * Created by vihaanverma on 17/01/18.
 */

public class CityUtil {

    public static String getCitySelectionText(City city){
        return new StringBuilder("Selection is - ").append(city.getName()).toString();
    }
}
