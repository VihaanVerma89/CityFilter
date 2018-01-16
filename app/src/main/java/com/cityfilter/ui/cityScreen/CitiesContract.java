package com.cityfilter.ui.cityScreen;

import com.cityfilter.network.models.City;
import com.cityfilter.network.models.CityData;
import com.cityfilter.ui.BasePresenter;
import com.cityfilter.ui.BaseView;

import java.util.List;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface CitiesContract {

    interface View extends BaseView<Presenter>{

        void showCities(List<City> cities);

        void showCitiesError(Throwable error);

        void hideProgressView();
    }

    interface Presenter extends BasePresenter{

    }
}