package com.cityfilter.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cityfilter.network.models.City;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;

/**
 * Created by vihaanverma on 16/01/18.
 */

@Dao
public interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCities(List<City> cities);

    @Query("SELECT * FROM " + Tables.CITIES)
    Single<List<City>> getCities();

    @Query("DELETE FROM "+ Tables.CITIES)
    void deleteAllCities();

}
