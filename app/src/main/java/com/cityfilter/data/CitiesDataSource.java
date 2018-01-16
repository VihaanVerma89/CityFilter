package com.cityfilter.data;

import com.cityfilter.network.models.City;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface CitiesDataSource {
    Single<List<City>> getCities();
    void setCities(List<City> cities);
}
