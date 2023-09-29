package com.example.ghelmory.gameInstanceStats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ghelmory.database.repository.GameRepository;
import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatsViewModel extends ViewModel {

    private final MutableLiveData<String> mTotalGamesPlayed = new MutableLiveData<>();
    private final MutableLiveData<String> mAVGScore = new MutableLiveData<>();
    private final MutableLiveData<String> mAVGTime = new MutableLiveData<>();
    private final MutableLiveData<List<PlayerViewItem>> mPlayers = new MutableLiveData<>();



    public LiveData<String> getTotal() {
        return mTotalGamesPlayed;
    }
    public LiveData<String> getAVGScore() {
        return mAVGScore;
    }
    public LiveData<String> getAVGTime() {
        return mAVGTime;
    }
    public LiveData<List<PlayerViewItem>> getPlayers() {
        return mPlayers;
    }

    public LiveData<List<GameInstance>> filterDisplayByGameId(UUID uuid) {
        if(uuid == null){
            return GameRepository.getInstance().getGameInstances();
        }
        return GameRepository.getInstance().filterDisplayByGameId(uuid);
    }

    public void setValues(List<GameInstance> gameInstances){
        if(gameInstances == null || gameInstances.size() == 0){
            mTotalGamesPlayed.setValue("0");
            mAVGScore.setValue("0");
            mAVGTime.setValue("0");
            mPlayers.setValue(new ArrayList<>());
            return;
        }
        List<PlayerViewItem> playersViewModelsList = new ArrayList<>();
        int total = gameInstances.size();
        int totalScore = 0;
        int totalTime = 0;
        int totalPlayers = 0;
        for (GameInstance gameInstance : gameInstances){
            for (Player player : gameInstance.getPlayers()){
                totalPlayers++;
                totalScore += player.getScore();
                playersViewModelsList.add(new PlayerViewItem(player));
            }
            totalTime += gameInstance.getPlaytime();
        }
        mTotalGamesPlayed.setValue(String.valueOf(total));
        float averageScore = (float) totalScore / totalPlayers;
        mAVGScore.setValue( String.format("%.2f", averageScore));
        float averageTime = (float) totalTime / totalPlayers;
        mAVGTime.setValue(String.format("%.2f", averageTime)+" min");
        mPlayers.setValue(playersViewModelsList);
    }
}