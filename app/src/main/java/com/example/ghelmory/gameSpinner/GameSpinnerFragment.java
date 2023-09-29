package com.example.ghelmory.gameSpinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.ghelmory.databinding.GameSpinnerBinding;
import com.example.ghelmory.viewItem.GameViewItem;

import java.util.ArrayList;
import java.util.UUID;

public class GameSpinnerFragment extends Fragment implements AdapterView.OnItemSelectedListener , ISelectedSpinnerValue{

    private GameSpinnerBinding binding;
    private GameSpinnerViewModel gameSpinnerViewModel;
    private ArrayAdapter<GameViewItem> adapter;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameSpinnerViewModel =
                new ViewModelProvider(this).get(GameSpinnerViewModel.class);
        binding = GameSpinnerBinding.inflate(inflater, container, false);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(gameSpinnerViewModel.getSelectGame());
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(this);

        // Return the root view from the data binding
        return binding.getRoot();
    }

    private void updateSpinnerData() {
        gameSpinnerViewModel.getGames().observe(getViewLifecycleOwner(), gameItems -> {
            adapter.clear();
            adapter.add(gameSpinnerViewModel.getSelectGame());
            adapter.addAll(gameSpinnerViewModel.getGameViewModels(gameItems));
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        GameViewItem selectedGame = (GameViewItem) parent.getItemAtPosition(position);
        if(!selectedGame.getGameName().equals("Select a game") && selectedGame.getGameDescription()!=null){
            gameSpinnerViewModel.setSelectedGameId(selectedGame.getGameId());
        }else{
            gameSpinnerViewModel.setSelectedGameId(null);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public LiveData<UUID> getSelectedGame() {
        return gameSpinnerViewModel.getSelectedGameIdLiveData();
    }

    @Override
    public void onResume() {
        updateSpinnerData();
        super.onResume();
    }
}
