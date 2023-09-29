package com.example.ghelmory.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ghelmory.database.dao.GameDao;
import com.example.ghelmory.database.dao.GameInstanceDao;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.model.GameInstance;

@Database(entities = {Game.class, GameInstance.class}, version = 1, exportSchema = false)
@TypeConverters({GameAndGameInstanceConverters.class})
public abstract class GameAndGameInstanceDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ghelmory_app_database";

    public abstract GameDao gameDao();

    public abstract GameInstanceDao gameInstanceDao();
    private static GameAndGameInstanceDatabase instance;

    public static GameAndGameInstanceDatabase getInstance() {
        if(instance == null)
            throw new IllegalStateException("database must be initialized");
        return  instance;
    }
    public static void initDatabase(Context context) {
        if(instance == null)
            instance = Room.
                    databaseBuilder(context.getApplicationContext(), GameAndGameInstanceDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
    }
    public static void disconnectDatabase() {
        instance = null;
    }


}
