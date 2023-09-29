package com.example.ghelmory.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ghelmory.model.GameInstance;

import java.util.List;
import java.util.UUID;

@Dao
public interface GameInstanceDao {
    @Insert
    void insert(GameInstance gameInstance);

    @Query("SELECT * FROM game_instance")
    LiveData<List<GameInstance>> getAllGameInstances();

    @Query("SELECT * FROM game_instance WHERE id = (:uuid)")
    LiveData<GameInstance> getGameInstance(UUID uuid);

    @Query("SELECT * FROM game_instance WHERE gameId = (:gameId)")
    LiveData<List<GameInstance>> filterDisplayByGameId(UUID gameId);
}
