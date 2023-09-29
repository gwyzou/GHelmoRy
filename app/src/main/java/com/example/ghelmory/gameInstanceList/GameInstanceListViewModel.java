package com.example.ghelmory.gameInstanceList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.GameInstance;

import java.util.List;
import java.util.UUID;

public class GameInstanceListViewModel extends ViewModel {
    public LiveData<List<GameInstance>> filterDisplayByGameId(UUID gameId) {
        if(gameId == null){
            return GameRepository.getInstance().getGameInstances();
        }
        return GameRepository.getInstance().filterDisplayByGameId(gameId);
    }


}