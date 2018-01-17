package com.cityfilter.ui.cityScreen;

import com.cityfilter.network.models.City;
import com.cityfilter.ui.BasePresenter;
import com.cityfilter.ui.BaseView;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface CitiesContract {

    interface View extends BaseView<Presenter> {

        void showCities(List<City> cities);

        void hideProgressView();

        void hideSwipeRefreshView();

        void showToast(String message, int lengthShort);
    }

    interface Presenter extends BasePresenter {

        void loadCities();

        void refreshCities();

        Single<List<City>> loadCities(String text);
    }
}
