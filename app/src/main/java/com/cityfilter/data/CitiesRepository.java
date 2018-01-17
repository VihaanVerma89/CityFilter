package com.cityfilter.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.cityfilter.network.models.City;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesRepository implements CitiesDataSource {

    @Nullable
    private static CitiesRepository INSTANCE = null;
    private CitiesDataSource mLocalDataSource, mRemoteDataSource;
    private List<City> mCityCache;


    @VisibleForTesting
    boolean mCacheIsDirty;

    private CitiesRepository(@NonNull CitiesDataSource citiesLocalDataSource,
                             @NonNull CitiesDataSource citiesRemoteDataSource) {
        mLocalDataSource = citiesLocalDataSource;
        mRemoteDataSource = citiesRemoteDataSource;
    }

    public static CitiesRepository getInstance(@NonNull CitiesDataSource tasksRemoteDataSource,
                                               @NonNull CitiesDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CitiesRepository(tasksLocalDataSource, tasksRemoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Single<List<City>> getCities() {
        if (mCityCache != null && !mCacheIsDirty) {
            return Single.just(mCityCache);
        } else if (mCityCache == null) {
            mCityCache = new ArrayList<>();
        }

        Single<List<City>> remoteCities = getAndSaveRemoteCity();

        if (mCacheIsDirty) {
            return remoteCities;
        } else {

            Single<List<City>> localCities = getAndCacheLocalCities();
            return Single
                    .concat(localCities, remoteCities)
                    .filter(cities -> !cities.isEmpty())
                    .firstOrError();
        }
    }

    @Override
    public void setCities(List<City> cities) {

    }

    @Override
    public void deleteAllCities() {
        mLocalDataSource.deleteAllCities();
    }

    private Single<List<City>> getAndSaveRemoteCity() {
        return mRemoteDataSource
                .getCities()
                .doOnSuccess(cities -> {
                    mLocalDataSource.setCities(cities);
                    mCacheIsDirty = false;
                });

    }

    private Single<List<City>> getAndCacheLocalCities() {
        return mLocalDataSource
                .getCities()
                .doOnSuccess(cities -> {
                    mCityCache = cities;
                });
    }

    public void setCacheDirty(){
        mCacheIsDirty = true;
    }
}
