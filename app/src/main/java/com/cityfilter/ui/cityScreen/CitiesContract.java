package com.cityfilter.ui.cityScreen;

import com.cityfilter.network.models.CityData;
import com.cityfilter.ui.BasePresenter;
import com.cityfilter.ui.BaseView;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface CitiesContract {

    interface View extends BaseView<Presenter>{

        void showCities(CityData cities);

        void showCitiesError(Throwable error);
    }

    interface Presenter extends BasePresenter{

    }
}
