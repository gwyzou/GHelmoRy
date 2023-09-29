package com.example.ghelmory.database.repository;

import androidx.lifecycle.LiveData;

import com.example.ghelmory.database.GameAndGameInstanceDatabase;
import com.example.ghelmory.database.dao.GameDao;
import com.example.ghelmory.database.dao.GameInstanceDao;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.model.GameInstance;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRepository {
    private static GameRepository instance;
    private final GameDao gameDao;
    private final GameInstanceDao gameInstanceDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private GameRepository(){
        GameAndGameInstanceDatabase instance = GameAndGameInstanceDatabase.getInstance();
        gameDao = instance.gameDao();
        gameInstanceDao = instance.gameInstanceDao();
    }
    public LiveData<List<GameInstance>> getGameInstances() {
        return gameInstanceDao.getAllGameInstances();
    }
    public static GameRepository getInstance() {
        if(instance == null)
            instance = new GameRepository();
        return instance;
    }

    public void insertGame(Game game) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameDao.insert(game);
            }
        });
    }
    public void insertGameInstance(GameInstance gameInstance) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                gameInstanceDao.insert(gameInstance);
            }
        });
    }
    public LiveData<List<Game>> getGames() {
        return gameDao.getAllGames();

    }
    public LiveData<Game> getGame(UUID uuid) {
        return gameDao.getGame(uuid);
    }
    public LiveData<GameInstance> getGameInstance(UUID uuid) {
        if(uuid == null){
            return null;
        }
        return gameInstanceDao.getGameInstance(uuid);
    }
    public LiveData<List<GameInstance>> filterDisplayByGameId(UUID gameId) {
        return gameInstanceDao.filterDisplayByGameId(gameId);
    }

}
