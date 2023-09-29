package com.example.ghelmory.gameSpinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.viewItem.GameViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSpinnerViewModel extends ViewModel {


    public LiveData<List<Game>> getGames() {
        return GameRepository.getInstance().getGames();
    }

    private final MutableLiveData<UUID> selectedGameIdLiveData = new MutableLiveData<>();


    public void setSelectedGameId(UUID selectedGameId) {
        selectedGameIdLiveData.setValue(selectedGameId);
    }
    public LiveData<UUID> getSelectedGameIdLiveData() {
        return selectedGameIdLiveData;
    }

    public GameViewItem getSelectGame(){
        Game newGame = new Game("Select a game", null, -1,-1,-1,null);
        newGame.setId(null);
        return new GameViewItem(newGame);
    }
    public List<GameViewItem> getGameViewModels(List<Game> games){
        if(games == null){
            return new ArrayList<>();
        }
        List<GameViewItem> gameViewItems = new ArrayList<>();
        for (Game game : games){
            gameViewItems.add(new GameViewItem(game));
        }
        return gameViewItems;
    }
}
