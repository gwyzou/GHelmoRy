package com.example.ghelmory.gameInstanceStats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.PlayerItemListDetailsBinding;
import com.example.ghelmory.model.GameInstance;
import com.example.ghelmory.model.Player;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.ArrayList;
import java.util.List;

public class StatsPlayerListAdapter extends RecyclerView.Adapter<StatsPlayerListAdapter.StatsPlayerViewHolder>{
    private final List<PlayerViewItem> players;

    public StatsPlayerListAdapter(List<GameInstance> gameInstances) {
        List<PlayerViewItem> players = new ArrayList<>();
        for (GameInstance gameInstance : gameInstances) {
            for (Player player : gameInstance.getPlayers()) {
                players.add(new PlayerViewItem(player));
            }
        }
        this.players = players;
    }


    @NonNull
    @Override
    public StatsPlayerListAdapter.StatsPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_list_details, parent, false);
        return new StatsPlayerListAdapter.StatsPlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsPlayerListAdapter.StatsPlayerViewHolder holder, int position) {
        PlayerViewItem playerViewItem = players.get(position);
        holder.bind(playerViewItem);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
    static class StatsPlayerViewHolder extends RecyclerView.ViewHolder{
        private final PlayerItemListDetailsBinding viewDataBinding;

        public StatsPlayerViewHolder(View view) {
            super(view);
            this.viewDataBinding = PlayerItemListDetailsBinding.bind(view);
        }
        public void bind(PlayerViewItem player) {
            viewDataBinding.playerName.setText(player.getPlayerName());
            viewDataBinding.playerScore.setText(String.valueOf(player.getPlayerScore()));
        }




    }
}
