package com.cityfilter;

import android.support.v4.widget.TextViewCompat;

import com.cityfilter.data.CitiesRemoteDataSource;
import com.cityfilter.data.CitiesRepository;
import com.cityfilter.network.models.City;
import com.cityfilter.ui.cityScreen.CitiesContract;
import com.cityfilter.ui.cityScreen.CitiesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vihaanverma on 17/01/18.
 */

public class CitiesPresenterTest {

    private List<City> mCities;

    @Mock
    private CitiesRepository mRepository;

    @Mock
    private CitiesContract.View mView;

    private CitiesPresenter mPresenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        CitiesRemoteDataSource remoteDataSource = CitiesRemoteDataSource.getInstance();
        mPresenter = new CitiesPresenter(mRepository, mView);
        mCities = remoteDataSource.getCities().blockingGet();
    }

    @Test
    public void createPresenter_setPresenterToView(){
        mPresenter=new CitiesPresenter(mRepository, mView);
        verify(mView).setPresenter(mPresenter);
    }

    @Test
    public void loadCitiesFromRepository(){
        when(mRepository.getCities()).thenReturn(Single.just(mCities));
        verify(mView).hideSwipeRefreshView();
        verify(mView).hideSwipeRefreshView();
    }





}

