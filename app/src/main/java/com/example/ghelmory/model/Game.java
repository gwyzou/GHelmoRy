package com.example.ghelmory.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;
@Entity(tableName = "game")
public class Game {
    @PrimaryKey
    @NonNull
    private UUID id;
    private  String name;
    private  String description;
    private  int numberOfPlayersMax;
    private  int numberOfPlayersMin;
    private  int estimatedTime;
    private  String photo;

    @Ignore
    public Game(String name, String description, int numberOfPlayersMin,int numberOfPlayersMax, int estimatedTime, String photo) {
        id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.numberOfPlayersMax = numberOfPlayersMax;
        this.numberOfPlayersMin = numberOfPlayersMin;

        this.estimatedTime = estimatedTime;
        this.photo = photo;
    }
    public Game(){
        id = UUID.randomUUID();
        this.name = null;
        this.description = null;
        this.numberOfPlayersMax = 0;
        this.numberOfPlayersMin = 0;
        this.estimatedTime = 0;
        this.photo = null;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfPlayersMax() {
        return numberOfPlayersMax;
    }

    public void setNumberOfPlayersMax(int numberOfPlayersMax) {
        this.numberOfPlayersMax = numberOfPlayersMax;
    }

    public int getNumberOfPlayersMin() {
        return numberOfPlayersMin;
    }

    public void setNumberOfPlayersMin(int numberOfPlayersMin) {
        this.numberOfPlayersMin = numberOfPlayersMin;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}