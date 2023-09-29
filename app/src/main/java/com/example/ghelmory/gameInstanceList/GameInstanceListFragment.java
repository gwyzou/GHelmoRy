package com.example.ghelmory.gameInstanceList;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.R;
import com.example.ghelmory.SubActivity;
import com.example.ghelmory.databinding.FragmentGameListBinding;
import com.example.ghelmory.gameInstanceDetails.GameInstanceDetailsFragment;
import com.example.ghelmory.gameSpinner.GameSpinnerFragment;
import com.example.ghelmory.gameSpinner.ISelectedSpinnerValue;

import java.util.Collections;
import java.util.UUID;

public class GameInstanceListFragment extends Fragment implements ISelectGameInstance{

    private FragmentGameListBinding binding;

    private GameInstanceAdapter adapter;
    private GameInstanceListViewModel gameInstanceListViewModel;
    private ISelectedSpinnerValue spinnerValueCallback;


    public static GameInstanceListFragment newInstance() {
        return new GameInstanceListFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameInstanceListViewModel =
                new ViewModelProvider(this).get(GameInstanceListViewModel.class);

        binding = FragmentGameListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        adapter = new GameInstanceAdapter(Collections.emptyList(), this);
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        setGameSpinnerFragment();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onStart() {
        super.onStart();
        spinnerValueCallback.getSelectedGame().observe(getViewLifecycleOwner(),
                uuid -> gameInstanceListViewModel.filterDisplayByGameId(uuid).observe(getViewLifecycleOwner(),
                        gameInstances -> adapter.updateData(gameInstances)));
    }

    private void setGameSpinnerFragment(){
        GameSpinnerFragment gameSpinnerFragment = new GameSpinnerFragment();
        spinnerValueCallback = gameSpinnerFragment;
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.game_spinner_container, gameSpinnerFragment);
        fragmentTransaction.commit();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSelectedGameInstance(UUID gameInstanceId) {
        Intent intent = new Intent(requireContext(), SubActivity.class);
        intent.putExtra("fragmentType", GameInstanceDetailsFragment.class.getSimpleName());
        intent.putExtra("gameInstanceId", gameInstanceId.toString());
        startActivity(intent);
    }
}