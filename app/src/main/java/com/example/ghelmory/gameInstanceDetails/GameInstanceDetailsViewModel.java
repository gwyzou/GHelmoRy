package com.example.ghelmory.gameInstanceDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.model.GameInstance;

import java.util.UUID;

public class GameInstanceDetailsViewModel extends ViewModel {

    public LiveData<GameInstance> getGameInstance(UUID gameUUID) {
        return GameRepository.getInstance().getGameInstance(gameUUID);
    }
    public LiveData<Game> getGame(UUID gameUUID) {
        return GameRepository.getInstance().getGame(gameUUID);
    }
}
