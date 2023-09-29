package com.example.ghelmory;

import android.app.Application;

import com.example.ghelmory.database.GameAndGameInstanceDatabase;

public class GHelmoRyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GameAndGameInstanceDatabase.initDatabase(getBaseContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        GameAndGameInstanceDatabase.disconnectDatabase();
    }
}
