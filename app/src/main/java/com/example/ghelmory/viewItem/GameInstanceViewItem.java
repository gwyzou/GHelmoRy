package com.example.ghelmory.viewItem;

import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameInstanceViewItem {
    private final GameInstance gameInstance;
    private List<PlayerViewItem> playerViewModelsList;
    public GameInstanceViewItem(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }
    public String getGameInstanceTitle() {
        return gameInstance.getTitle();
    }
    public String getGameInstanceDescription() {
        return gameInstance.getDescription();
    }
    public String getGameInstanceImage() {
        return gameInstance.getPhoto();
    }
    public UUID getGameInstanceId() {
        return gameInstance.getId();
    }
    public UUID getGameInstanceGameId() {
        return gameInstance.getGameId();
    }
    public int getGameInstancePlaytime() {
        return gameInstance.getPlaytime();
    }
    public String getGameInstanceLocation() {
        return gameInstance.getLocation();
    }
    public List<PlayerViewItem> getGameInstancePlayers() {
        if(playerViewModelsList==null){
            setPlayerViewModelsList();
        }
        return playerViewModelsList;
    }
    private void setPlayerViewModelsList(){
        playerViewModelsList = new ArrayList<>();
        for(Player player : gameInstance.getPlayers()){
            playerViewModelsList.add(new PlayerViewItem(player));
        }
    }

}
