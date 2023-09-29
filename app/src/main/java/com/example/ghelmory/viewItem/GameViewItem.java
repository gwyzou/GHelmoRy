package com.example.ghelmory.viewItem;

import com.example.ghelmory.model.Game;

import java.util.UUID;

public class GameViewItem {
    private final Game game;
    public GameViewItem(Game game) {
        this.game = game;
    }
    public String getGameName() {
        return game.getName();
    }
    public String getGameDescription() {
        return game.getDescription();
    }
    public String getGameImage() {
        return game.getPhoto();
    }
    public int getGameMinPlayers() {
        return game.getNumberOfPlayersMin();
    }
    public int getGameMaxPlayers() {
        return game.getNumberOfPlayersMax();
    }
    public int getGameEstimatedTime() {
        return game.getEstimatedTime();
    }
    public UUID getGameId() {
        return game.getId();
    }

    @Override
    public String toString(){
        return this.getGameName();
    }
}
