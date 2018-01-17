package com.cityfilter.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cityfilter.data.local.AppDatabase;
import com.cityfilter.data.local.CitiesLocalDataSource;
import com.cityfilter.utils.schedulers.BaseSchedulerProvider;
import com.cityfilter.utils.schedulers.SchedulerProvider;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class Injection {
    public static CitiesRepository provideCitiesRepository(@NonNull Context context) {
        return CitiesRepository.getInstance(CitiesRemoteDataSource.getInstance(),
                CitiesLocalDataSource.getInstance(AppDatabase.getInstance(context).citiesDao()));
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

}
