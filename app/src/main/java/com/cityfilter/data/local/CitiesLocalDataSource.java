package com.cityfilter.data.local;

import android.support.annotation.Nullable;

import com.cityfilter.data.CitiesDataSource;
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

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Single<List<City>> getCities() {
        return mCitiesDao.getCities();
    }

    @Override
    public void setCities(List<City> cities) {
        mCitiesDao.insertCities(cities);
    }

    @Override
    public void deleteAllCities() {
       mCitiesDao.deleteAllCities();
    }

    @Override
    public Single<List<City>> getCities(String text) {
        return mCitiesDao.getCities(text);
    }
}
