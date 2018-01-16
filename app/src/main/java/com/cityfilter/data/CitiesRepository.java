package com.cityfilter.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cityfilter.network.models.City;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesRepository implements CitiesDataSource {

    @Nullable
    private static CitiesRepository INSTANCE = null;
    private CitiesDataSource mLocalDataSource, mRemoteDataSource;

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

        Single<List<City>> remoteCities = getAndSaveRemoteCity();
        Single<List<City>> localCities = getLocalCities();

       return Single
               .concat(localCities, remoteCities)
               .filter(cities -> !cities.isEmpty())
               .firstOrError();
    }

    @Override
    public void setCities(List<City> cities) {

    }

    private Single<List<City>> getAndSaveRemoteCity() {
        return mRemoteDataSource
                .getCities()
                .doOnSuccess(cities ->{
                    mLocalDataSource.setCities(cities);
                });

    }

    private Single<List<City>> getLocalCities(){
        return mLocalDataSource.getCities();
    }
}
