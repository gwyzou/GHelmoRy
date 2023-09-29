package com.example.ghelmory.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.UUID;

@Entity(tableName = "game_instance",
        foreignKeys = @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "gameId"))

public class GameInstance {

    @PrimaryKey
    @NonNull
    private UUID id;
    @ColumnInfo(index = true)
    private UUID gameId;
    private String title;
    private List<Player> players;

    private  String description;
    private  int playtime;
    private  String location;
    private  String photo;


    public GameInstance(){
        id = UUID.randomUUID();
        this.title = null;
        this.gameId = null;
        this.players = null;
        this.description = null;
        this.playtime = 0;
        this.location = null;
        this.photo = null;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    @NonNull
    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(@NonNull UUID gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
