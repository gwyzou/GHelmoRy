package com.example.ghelmory.gameInstanceDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.PlayerItemListDetailsBinding;
import com.example.ghelmory.viewItem.GameInstanceViewItem;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.List;

public class GameInstanceDetailsFragmentPlayerAdapter extends RecyclerView.Adapter<GameInstanceDetailsFragmentPlayerAdapter.ViewHolder> {
    private final List<PlayerViewItem> players;

    public GameInstanceDetailsFragmentPlayerAdapter(GameInstanceViewItem gameInstance) {
        this.players =gameInstance.getGameInstancePlayers();
    }
    @NonNull
    @Override
    public GameInstanceDetailsFragmentPlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_list_details, parent, false);
        return new GameInstanceDetailsFragmentPlayerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameInstanceDetailsFragmentPlayerAdapter.ViewHolder holder, int position) {
        PlayerViewItem playerViewItem = players.get(position);
        holder.bind(playerViewItem);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final PlayerItemListDetailsBinding viewDataBinding;



        public ViewHolder(View view) {
            super(view);
            this.viewDataBinding = PlayerItemListDetailsBinding.bind(view);
        }
        public void bind(PlayerViewItem player) {
            viewDataBinding.playerName.setText(player.getPlayerName());
            viewDataBinding.playerScore.setText(String.valueOf(player.getPlayerScore()));
        }




    }
}
