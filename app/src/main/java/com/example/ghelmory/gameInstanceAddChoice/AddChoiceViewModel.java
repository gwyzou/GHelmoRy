package com.example.ghelmory.gameInstanceAddChoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;
import com.example.ghelmory.utilities.DataValidator;
import com.example.ghelmory.utilities.SharedDataAcrossActivitiesManager;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddChoiceViewModel extends ViewModel {

    private MutableLiveData<List<PlayerViewItem>> playerListLiveData = new MutableLiveData<>();
    private List<PlayerViewItem> playerList = new ArrayList<>();

    private MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> getImageUrlLiveData() {
        return imageUrl;
    }
    public LiveData<List<PlayerViewItem>> getPlayerListLiveData() {
        return playerListLiveData;
    }


    public void addPlayer(String playerName, int playerScore) {
        playerList.add(new PlayerViewItem(playerName, playerScore));
        playerListLiveData.setValue(playerList);
    }

    public void removePlayer(PlayerViewItem player) {
        playerList.remove(player);
        playerListLiveData.setValue(playerList);
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl.setValue(imageUrl);
    }

    public boolean saveGameInstance(String title, String description, String playTime, UUID gameId){
        String imageUrl = getImageUrlLiveData().getValue();
        String address = SharedDataAcrossActivitiesManager.getInstance().getSharedData();
        if(hasCorrectInput(title,description,playTime,imageUrl,address,gameId) && playerList.size() > 0){
            GameInstance gameInstance = new GameInstance();
            gameInstance.setTitle(title);
            gameInstance.setDescription(description);
            gameInstance.setPlaytime(Integer.parseInt(playTime));
            gameInstance.setPhoto(imageUrl);
            gameInstance.setGameId(gameId);
            gameInstance.setLocation(address);
            gameInstance.setPlayers(new ArrayList<Player>() {
                {
                    for(PlayerViewItem player : playerList){
                        add(new Player(player.getPlayerName(), player.getPlayerScore()));
                    }
                }
            });
            GameRepository.getInstance().insertGameInstance(gameInstance);

            return true;
        }
        return false;
    }

    private boolean hasCorrectInput(String title, String description, String playTime, String imageUrl,String address, UUID gameId) {
        boolean titleCorrect = DataValidator.isStringValid(title);
        boolean descriptionCorrect = DataValidator.isStringValid(description);
        boolean playTimeCorrect = DataValidator.isValidPositiveIntString(playTime);
        boolean addressCorrect = address==null || !address.isEmpty();
        boolean imageUrlCorrect =imageUrl==null|| !imageUrl.isEmpty();
        boolean gameIdCorrect = gameId != null ;
        return titleCorrect && descriptionCorrect && playTimeCorrect && addressCorrect && imageUrlCorrect && gameIdCorrect;
    }
    public boolean tempoMethod(String title, String description, String playTime, UUID gameId){
        String imageUrl = getImageUrlLiveData().getValue();
        String address = SharedDataAcrossActivitiesManager.getInstance().getSharedData();
        return hasCorrectInput(title,description,playTime,imageUrl,address,gameId) && playerList.size() > 0;
    }
}