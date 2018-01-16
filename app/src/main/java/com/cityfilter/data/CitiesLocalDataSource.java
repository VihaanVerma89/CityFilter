package com.cityfilter.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cityfilter.network.models.CityData;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesLocalDataSource implements CitiesDataSource {

    @Nullable
    private static CitiesLocalDataSource INSTANCE;

    private  CitiesLocalDataSource(){

    }

    public static CitiesLocalDataSource getInstance( ){
        if (INSTANCE == null) {
            INSTANCE = new CitiesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<CityData> getCities() {
        return null;
    }
}
