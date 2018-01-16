package com.cityfilter.network;

import com.cityfilter.network.models.CityData;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by vihaanverma on 16/01/18.
 */

public interface ApiClient {
    public static final String BASE_URL = "https://api.aasaanjobs.com/api/v4/";

    @GET("/city")
    Single<CityData> getCities();

}
