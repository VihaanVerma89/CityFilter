package com.cityfilter.data;

import com.cityfilter.network.ApiClient;
import com.cityfilter.network.models.City;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesRemoteDataSource implements CitiesDataSource {

    private static CitiesRemoteDataSource INSTANCE;

    private CitiesRemoteDataSource(){

    }

    public static CitiesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CitiesRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<List<City>> getCities() {

        //TODO
        // inject with dagger
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        return apiClient.getCities().map(cityData ->cityData.getCities());
    }

    @Override
    public void setCities(List<City> cities) {
        // update at server.
    }

    @Override
    public void deleteAllCities() {
        // delete at server
    }

    @Override
    public Single<List<City>> getCities(String text) {
        // search from server db
        return null;
    }
}
