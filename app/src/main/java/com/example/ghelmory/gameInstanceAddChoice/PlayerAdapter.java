package com.example.ghelmory.gameInstanceAddChoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.PlayerItemListBinding;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    public interface OnRemovePlayerCallback {
        void onRemovePlayer(PlayerViewItem player);
    }
    private List<PlayerViewItem> playerList = new ArrayList<>();
    private final OnRemovePlayerCallback callback;

    public PlayerAdapter(OnRemovePlayerCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_list, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        PlayerViewItem currentPlayer = playerList.get(position);
        holder.binding.playerName.setText(currentPlayer.getPlayerName());
        holder.binding.playerScore.setText(String.valueOf(currentPlayer.getPlayerScore()));


        holder.binding.playerRemoveButton.setOnClickListener(v -> callback.onRemovePlayer(currentPlayer));
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public void setPlayers(List<PlayerViewItem> players) {
        this.playerList = players;
        notifyDataSetChanged();

    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {

        public PlayerItemListBinding binding;

        PlayerViewHolder(View itemView) {
            super(itemView);
            binding = PlayerItemListBinding.bind(itemView);

        }
    }
}

