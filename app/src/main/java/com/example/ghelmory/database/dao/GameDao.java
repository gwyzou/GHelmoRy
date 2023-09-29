package com.example.ghelmory.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ghelmory.model.Game;

import java.util.List;
import java.util.UUID;

@Dao
public interface GameDao {
    @Insert
    void insert(Game game);

    @Query("SELECT * FROM game ORDER BY name ASC")
    LiveData<List<Game>> getAllGames();

    @Query("SELECT * FROM game WHERE id = (:uuid)")
    LiveData<Game> getGame(UUID uuid);

}
