package com.cityfilter.data;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class Injection {
    public static CitiesRepository provideCitiesRepository(@NonNull Context context) {
        return CitiesRepository.getInstance(CitiesRemoteDataSource.getInstance(),
                CitiesLocalDataSource.getInstance());
    }


}
