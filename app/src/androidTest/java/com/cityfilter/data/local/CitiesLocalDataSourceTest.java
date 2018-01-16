package com.cityfilter.data.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cityfilter.data.CitiesRemoteDataSource;
import com.cityfilter.network.models.City;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * Created by vihaanverma on 17/01/18.
 */

public class CitiesLocalDataSourceTest {

//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CitiesLocalDataSource mLocalDataSource;
    private AppDatabase mDatabase;
    private List<City> mCities;

    @Before
    public void setup(){
        CitiesLocalDataSource.destroyInstance();
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        mLocalDataSource= CitiesLocalDataSource.getInstance(mDatabase.citiesDao());
        mCities = CitiesRemoteDataSource.getInstance().getCities().blockingGet();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertAndGetCities(){
        mLocalDataSource.setCities(mCities);

        mLocalDataSource.getCities()
                .test()
                .assertValue(cities -> mCities.equals(cities));

    }

    @Test
    public void testDelete(){
        mLocalDataSource.deleteAllCities();
        mLocalDataSource.getCities()
                .test()
                .assertValue(cities->cities.size()==0);
    }




}
