package com.example.ghelmory.gameInstanceStats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.ghelmory.R;
import com.example.ghelmory.databinding.FragmentStatsBinding;
import com.example.ghelmory.gameSpinner.GameSpinnerFragment;
import com.example.ghelmory.gameSpinner.ISelectedSpinnerValue;

import java.util.ArrayList;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;
    private ISelectedSpinnerValue spinnerValueCallback;
    private StatsViewModel statsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setSpinnerFragment();
        binding.recyclerViewStats.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewStats.setAdapter(new StatsPlayerListAdapter(new ArrayList<>()));
        statsViewModel.getTotal().observe(getViewLifecycleOwner(), value->{
            binding.textStatsTotalGamesDisplay.setText(value.toString());
        });
        statsViewModel.getAVGScore().observe(getViewLifecycleOwner(), value->{
            binding.textStatsAverageScoreDisplay.setText(value.toString());
        });
        statsViewModel.getAVGTime().observe(getViewLifecycleOwner(), value->{
            binding.textStatsAverageTimeDisplay.setText(value.toString());
        });

        return root;
    }
    private void setSpinnerFragment(){
        GameSpinnerFragment gameSpinnerFragment = new GameSpinnerFragment();
        spinnerValueCallback = gameSpinnerFragment;
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.game_spinner_container, gameSpinnerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        spinnerValueCallback.getSelectedGame().observe(getViewLifecycleOwner(),
                uuid -> statsViewModel.filterDisplayByGameId(uuid).observe(getViewLifecycleOwner(),
                        gameInstances -> {
                            statsViewModel.setValues(gameInstances);
                            binding.recyclerViewStats.setAdapter(new StatsPlayerListAdapter(gameInstances));
                        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}