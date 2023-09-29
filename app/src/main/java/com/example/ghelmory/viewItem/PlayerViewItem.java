package com.example.ghelmory.viewItem;

import androidx.annotation.NonNull;

import com.example.ghelmory.model.Player;

public class PlayerViewItem {
    private final Player player;
    public PlayerViewItem(Player player) {
        this.player = player;
    }
    public PlayerViewItem(String playerName, int playerScore) {
        this.player = new Player(playerName, playerScore);
    }
    public String getPlayerName() {
        return player.getName();
    }
    public int getPlayerScore() {
        return player.getScore();
    }
    @NonNull
    @Override
    public String toString(){
        String name = this.player.getName();
        int score = this.player.getScore();
        if(name==null || name.isEmpty()){
            return "Player " + score;
        }
        return name + " " + score;
    }
}
