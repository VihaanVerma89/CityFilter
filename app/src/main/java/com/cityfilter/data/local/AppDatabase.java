package com.cityfilter.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cityfilter.network.models.City;

/**
 * Created by vihaanverma on 16/01/18.
 */

@Database(entities = {City.class}, version =1)
public abstract class AppDatabase extends RoomDatabase{


    private static volatile AppDatabase INSTANCE;
    public abstract CitiesDao citiesDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
