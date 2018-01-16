package com.cityfilter.data;

import com.cityfilter.network.models.CityData;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface CitiesDataSource {
    Single<CityData> getCities();
}
