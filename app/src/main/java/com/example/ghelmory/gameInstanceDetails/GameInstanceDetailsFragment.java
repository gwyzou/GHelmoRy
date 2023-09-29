package com.example.ghelmory.gameInstanceDetails;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ghelmory.databinding.FragmentDetailGameInstanceBinding;
import com.example.ghelmory.utilities.DataValidator;
import com.example.ghelmory.viewItem.GameInstanceViewItem;
import com.example.ghelmory.viewItem.GameViewItem;

import java.util.UUID;

public class GameInstanceDetailsFragment extends Fragment {

    private FragmentDetailGameInstanceBinding binding;
    private GameInstanceDetailsViewModel gameInstanceDetailsViewModel;
    private UUID gameInstanceId;


    public static GameInstanceDetailsFragment newInstance(UUID gameInstanceId){
        GameInstanceDetailsFragment fragment = new GameInstanceDetailsFragment();
        fragment.gameInstanceId = gameInstanceId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gameInstanceDetailsViewModel= new GameInstanceDetailsViewModel();
        binding = FragmentDetailGameInstanceBinding.inflate(inflater, container, false);

        gameInstanceDetailsViewModel.getGameInstance(gameInstanceId).observe(getViewLifecycleOwner(), gameInstance -> {
            bindGameInstance(new GameInstanceViewItem(gameInstance));
            gameInstanceDetailsViewModel.getGame(gameInstance.getGameId()).observe(getViewLifecycleOwner(), game -> {
                bindGame(new GameViewItem(game));
            });
        });

        return binding.getRoot();
    }




    private void bindGameInstance( GameInstanceViewItem gameInstanceViewItem){
        binding.historyTitle.setText(gameInstanceViewItem.getGameInstanceTitle());
        binding.historyDescription.setText(gameInstanceViewItem.getGameInstanceDescription());
        binding.historyPlaytime.setText(String.valueOf(gameInstanceViewItem.getGameInstancePlaytime()));
        GameInstanceDetailsFragmentPlayerAdapter adapter = new GameInstanceDetailsFragmentPlayerAdapter(gameInstanceViewItem);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(adapter);
        if(gameInstanceViewItem.getGameInstanceImage()!=null && DataValidator.isValidUrl(gameInstanceViewItem.getGameInstanceImage())){
            Uri imageUri = Uri.parse(gameInstanceViewItem.getGameInstanceImage());
            binding.historyImageView.setImageURI(imageUri);
        }
        binding.historyLocation.setText(gameInstanceViewItem.getGameInstanceLocation());
    }
    private void bindGame(GameViewItem gameViewItem){
        binding.gameInfoTitle.setText(gameViewItem.getGameName());
        binding.gameInfoPlaytime.setText(String.valueOf(gameViewItem.getGameEstimatedTime()));
        binding.gameInfoDescription.setText(gameViewItem.getGameDescription());
        String gamePlayerNumber = gameViewItem.getGameMinPlayers()+"-"+ gameViewItem.getGameMaxPlayers();
        binding.gameInfoPlayerNumber.setText(gamePlayerNumber);
        if(gameViewItem.getGameImage()!=null){
            Uri imageUri = Uri.parse(gameViewItem.getGameImage());
            binding.gameInfoImageView.setImageURI(imageUri);
        }
    }
}
