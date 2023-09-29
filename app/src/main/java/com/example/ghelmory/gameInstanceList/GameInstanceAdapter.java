package com.example.ghelmory.gameInstanceList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.GameItemListBinding;
import com.example.ghelmory.model.GameInstance;

import java.util.List;
import java.util.UUID;

public class GameInstanceAdapter extends RecyclerView.Adapter<GameInstanceAdapter.ViewHolder> {

    private List<GameInstance> gameInstances;
    private final ISelectGameInstance callBacks;

    public GameInstanceAdapter(List<GameInstance> gameInstances, ISelectGameInstance callBacks) {
        this.gameInstances = gameInstances;
        this.callBacks = callBacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item_list, parent, false);
        return new ViewHolder(view, callBacks);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameInstance gameInstance = gameInstances.get(position);
        holder.bind(gameInstance);
    }

    @Override
    public int getItemCount() {
        return gameInstances.size();
    }

    public void updateData(List<GameInstance> gameInstances) {
        this.gameInstances = gameInstances;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final GameItemListBinding viewDataBinding;
        private final ISelectGameInstance callBacks;
        private UUID gameInstanceId;


        public ViewHolder(View view,ISelectGameInstance callBacks) {
            super(view);
            this.viewDataBinding = GameItemListBinding.bind(view);

            this.callBacks = callBacks;
            view.setOnClickListener(this);
        }
        public void bind(GameInstance gameInstance) {
            //viewDataBinding.setViewModel(new GameInstanceViewModel(gameInstance));
            viewDataBinding.gameInstanceTitle.setText(gameInstance.getTitle());
            gameInstanceId = gameInstance.getId();
        }
        @Override
        public void onClick(View view) {
            if (callBacks != null) {
                callBacks.onSelectedGameInstance(gameInstanceId);
            }
        }



    }


}


