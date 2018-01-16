package com.cityfilter.data;

import android.support.annotation.Nullable;

import com.cityfilter.data.local.CitiesDao;
import com.cityfilter.network.models.City;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesLocalDataSource implements CitiesDataSource {

    private CitiesDao mCitiesDao;

    @Nullable
    private static CitiesLocalDataSource INSTANCE;

    private  CitiesLocalDataSource(CitiesDao citiesDao){
        mCitiesDao=citiesDao;
    }

    public static CitiesLocalDataSource getInstance(CitiesDao citiesDao){
        if (INSTANCE == null) {

            INSTANCE = new CitiesLocalDataSource(citiesDao);
        }
        return INSTANCE;
    }

    @Override
    public Single<List<City>> getCities() {
        return mCitiesDao.getCities();
    }

    @Override
    public void setCities(List<City> cities) {
        mCitiesDao.insertCities(cities);
    }
}
