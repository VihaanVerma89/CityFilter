package com.cityfilter.ui.cityScreen;

import android.support.annotation.NonNull;

import com.cityfilter.network.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesPresenter implements CitiesContract.Presenter {

    @NonNull
    private CitiesContract.View mView;


    public CitiesPresenter(@NonNull CitiesContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        apiClient.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                            mView.hideProgressView();
                            mView.showCities(cities);
                        },
                        error -> {
                            mView.showCitiesError(error);
                        });
    }


    @Override
    public void unsubscribe() {

    }
}
