package com.cityfilter.ui.cityScreen;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.cityfilter.data.CitiesRepository;
import com.cityfilter.network.ApiClient;
import com.cityfilter.network.models.City;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesPresenter implements CitiesContract.Presenter {

    @NonNull
    private CitiesContract.View mView;
    @NonNull
    private CitiesRepository mRepository;

    public CitiesPresenter(@NonNull CitiesRepository repository, @NonNull CitiesContract.View
            view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadCities();
    }


    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadCities() {
        mRepository
                .getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                            showCities(cities);
                        },
                        error -> {
                            processError(error);
                        });
    }

    @Override
    public void refreshCities() {
        mRepository.refreshCities();
        loadCities();
    }

    private void showCities(List<City> cities) {
        mView.hideProgressView();
        mView.hideSwipeRefreshView();
        mView.showCities(cities);
    }

    private void processError(Throwable error) {
        if (error instanceof HttpException) {
            HttpException exception = (HttpException) error;
            mView.showToast(exception.getMessage(), Toast.LENGTH_SHORT);
        } else if (error instanceof UnknownHostException) {
            mView.showToast("Unable to reach network..", Toast.LENGTH_SHORT);
        }
    }
}
