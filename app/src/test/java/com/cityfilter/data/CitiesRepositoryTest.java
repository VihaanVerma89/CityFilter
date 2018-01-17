package com.cityfilter.data;

import com.cityfilter.network.models.City;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vihaanverma on 17/01/18.
 */

public class CitiesRepositoryTest {

    private CitiesRepository mRepository;
    private TestSubscriber<List<City>> mCitiesTestSubscriber;

    @Mock
    private CitiesDataSource mCitiesLocalDataSource;
    @Mock
    private CitiesDataSource mCitiesRemoteDataSource;
    private List<City> mCities;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mRepository = CitiesRepository.getInstance(mCitiesRemoteDataSource, mCitiesLocalDataSource);
        mCitiesTestSubscriber = new TestSubscriber<>();
        CitiesRemoteDataSource remoteDataSource = CitiesRemoteDataSource.getInstance();
        mCities = remoteDataSource.getCities().blockingGet();
    }

    @After
    public void destroyRepository(){
        CitiesRepository.destroyInstance();
    }

    @Test
    public void getCitiesFromLocalDataSource(){
        setCitiesAvailable(mCitiesLocalDataSource, mCities);
        setCitiesNotAvailable(mCitiesRemoteDataSource);

        TestSubscriber<List<City>> subscriber1 = new TestSubscriber<>();
        mRepository.getCities().toFlowable().subscribe(subscriber1);

        verify(mCitiesLocalDataSource).getCities();
        verify(mCitiesRemoteDataSource).getCities();

        assertFalse(mRepository.mCacheIsDirty);

        subscriber1.assertValue(mCities);
    }

    @Test
    public void getCitiesFromRemoteDataSource(){
        setCitiesAvailable(mCitiesRemoteDataSource, mCities);
        setCitiesNotAvailable(mCitiesLocalDataSource);

        TestSubscriber<List<City>> subscriber1 = new TestSubscriber<>();
        mRepository.getCities().toFlowable().subscribe(subscriber1);

        verify(mCitiesLocalDataSource).getCities();
        verify(mCitiesRemoteDataSource).getCities();

        assertFalse(mRepository.mCacheIsDirty);

        subscriber1.assertValue(mCities);
    }

    @Test
    public void getCitiesAfterSwipeRefresh(){
        setCitiesAvailable(mCitiesRemoteDataSource, mCities);

        mRepository.setCacheDirty();

        TestSubscriber<List<City>> subscriber = new TestSubscriber<>();
        mRepository.getCities().toFlowable().subscribe(subscriber);

        verify(mCitiesLocalDataSource, never()).getCities();
        verify(mCitiesRemoteDataSource).getCities();

        subscriber.assertValue(mCities);
    }

    private void setCitiesAvailable(CitiesDataSource dataSource, List<City> cities){
        when(dataSource.getCities()).thenReturn(Single.just(cities));
    }

    private void setCitiesNotAvailable(CitiesDataSource dataSource) {
        when(dataSource.getCities()).thenReturn(Single.just(Collections.emptyList()));
    }

}
