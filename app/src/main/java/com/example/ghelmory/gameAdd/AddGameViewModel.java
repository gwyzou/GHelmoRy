package com.example.ghelmory.gameAdd;

import androidx.lifecycle.MutableLiveData;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.Game;
import com.example.ghelmory.utilities.DataValidator;

public class AddGameViewModel {
    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 99;
    private static final int MIN_ESTIMATED_TIME = 1;
    private static final int MAX_ESTIMATED_TIME = 9999;
    private MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> getImageUrlLiveData() {
        return imageUrl;
    }
    public boolean saveGame(String title, String description, String minPlayers, String maxPlayers, String estimatedTime) {
        if (verifyData(title, description, minPlayers, maxPlayers, estimatedTime)) {
            if(!DataValidator.isValidUrl(imageUrl.getValue())){
                imageUrl.setValue(null);
            }
            Game game = new Game(title, description, Integer.parseInt(minPlayers), Integer.parseInt(maxPlayers), Integer.parseInt(estimatedTime), imageUrl.getValue());
            GameRepository.getInstance().insertGame(game);
            return true;
        }
        return false;
    }
    public void setImageUrl(String imageUrl) {
        if(imageUrl!=null && !imageUrl.isEmpty() && DataValidator.isValidUrl(imageUrl)){
            this.imageUrl.setValue(imageUrl);
        }
    }


    private boolean verifyData(String title, String description, String minPlayers, String maxPlayers, String estimatedTime) {
        boolean titleOk = DataValidator.isStringValid(title);
        boolean descriptionOk = DataValidator.isStringValid(description);
        boolean borderOk=verifyPlayersNumberBorders(minPlayers, maxPlayers);
        boolean estimatedTimeOk = DataValidator.isWithinRange(estimatedTime, MIN_ESTIMATED_TIME, MAX_ESTIMATED_TIME);
        return titleOk && descriptionOk && borderOk && estimatedTimeOk;
    }

    private boolean verifyPlayersNumberBorders(String minPlayers, String maxPlayers) {

        if(DataValidator.isValidPositiveIntString(minPlayers) && DataValidator.isValidPositiveIntString(maxPlayers)){
            int min = Integer.parseInt(minPlayers);
            int max = Integer.parseInt(maxPlayers);
            return DataValidator.isWithinRange(min, MIN_PLAYERS, max) && DataValidator.isWithinRange(max, min, MAX_PLAYERS);
        }
        return false;
    }




}
