package com.example.ghelmory.database;

import androidx.room.TypeConverter;

import com.example.ghelmory.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
public class GameAndGameInstanceConverters {

    private final Gson gson = new Gson();

    @TypeConverter
    public List<Player> fromString(String value) {
        Type listType = new TypeToken<List<Player>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public String toString(List<Player> players) {
        Type token = new TypeToken<List<Player>>() {}.getType();
        return gson.toJson(players,token);
    }
}
