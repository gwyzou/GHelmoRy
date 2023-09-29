package com.example.ghelmory.gameInstanceAddChoice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghelmory.gameSpinner.ISelectedSpinnerValue;
import com.example.ghelmory.utilities.ImagePicker;
import com.example.ghelmory.R;
import com.example.ghelmory.SubActivity;
import com.example.ghelmory.databinding.FragmentAddchoiceBinding;
import com.example.ghelmory.gameAdd.AddGameFragment;
import com.example.ghelmory.gameInstanceLocation.LocationFragment;
import com.example.ghelmory.gameSpinner.GameSpinnerFragment;
import com.example.ghelmory.viewItem.PlayerViewItem;

import java.util.UUID;

public class AddChoiceFragment extends Fragment implements PlayerAdapter.OnRemovePlayerCallback{

    private FragmentAddchoiceBinding binding;
    private PlayerAdapter playerAdapter;
    private AddChoiceViewModel addChoiceViewModel;
    private ISelectedSpinnerValue selectGameInstanceCallback;

    private final ImagePicker imagePicker = new ImagePicker();
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result->{
                addChoiceViewModel.setImageUrl(imagePicker.onImagePickedResult(result,getContext()));
            }
    );


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         addChoiceViewModel =
                new ViewModelProvider(this).get(AddChoiceViewModel.class);

        binding = FragmentAddchoiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setGameSpinnerFragment();

        RecyclerView playerRecyclerView = binding.playerList;
        playerRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        playerAdapter = new PlayerAdapter(this);
        playerRecyclerView.setAdapter(playerAdapter);

        setViewModelObservers();

        setAddPlayerButtonBinding();
        setChooseImageButtonBinding();
        setNewGameButtonBinding();
        setAddHistoryButtonBinding();
        setSelectLocationButton();
        return root;
    }
    private void setGameSpinnerFragment(){
        GameSpinnerFragment gameSpinnerFragment = new GameSpinnerFragment();
        selectGameInstanceCallback = gameSpinnerFragment;
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.game_spinner_container, gameSpinnerFragment);
        fragmentTransaction.commit();

    }

    private void setViewModelObservers() {
        addChoiceViewModel.getPlayerListLiveData().observe(getViewLifecycleOwner(), players -> {
            playerAdapter.setPlayers(players);
        });

        addChoiceViewModel.getImageUrlLiveData().observe(getViewLifecycleOwner(), imageUrl -> {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Uri imageUri = Uri.parse(imageUrl);
                binding.imageView.setImageURI(imageUri);
            } else {
                binding.imageView.setImageResource(R.drawable.baseline_image_24);
            }
        });
    }

    private void setAddPlayerButtonBinding() {
        EditText playerNameEditText = binding.addPlayerNameEdit;
        EditText playerScoreEditText = binding.addPlayerScoreEdit;
        Button addPlayerButton = binding.addPlayerButton;
        addPlayerButton.setOnClickListener(view -> {
            String playerName = playerNameEditText.getText().toString();
            String playerScore = playerScoreEditText.getText().toString();
            if (playerName.isEmpty() || playerScore.isEmpty()) {
                return;
            }
            addChoiceViewModel.addPlayer(playerName, Integer.parseInt(playerScore));
            playerNameEditText.setText("");
            playerScoreEditText.setText("");
        });
    }
    private void setNewGameButtonBinding() {
        Button newGameButton = binding.addGameButton;
        newGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), SubActivity.class);
            intent.putExtra("fragmentType", AddGameFragment.class.getSimpleName());
            startActivity(intent);

        });
    }
    private void setSelectLocationButton(){
        Button selectLocationButton = binding.selectLocationButton;
        selectLocationButton.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), SubActivity.class);
            intent.putExtra("fragmentType", LocationFragment.class.getSimpleName());
            startActivity(intent);
        });
    }


    private void setAddHistoryButtonBinding() {
        binding.addToHistoryButton.setOnClickListener(view -> {
            UUID gameId = selectGameInstanceCallback.getSelectedGame().getValue();
            String title = binding.titleEdit.getText().toString();
            String description = binding.descriptionEdit.getText().toString();
            String estimatedTime = binding.playtimeEdit.getText().toString();

            if(addChoiceViewModel.saveGameInstance(title, description, estimatedTime,gameId)) {
                Toast.makeText(requireContext(), "History added", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            }else{
                Toast.makeText(requireContext(), "History not added, There is invalid input", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setChooseImageButtonBinding() {
        binding.chooseImageButton
                .setOnClickListener(view -> {
                    imagePicker.runImagePicker(requireContext(),requireActivity(), imagePickerLauncher);
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRemovePlayer(PlayerViewItem player) {
        addChoiceViewModel.removePlayer(player);
    }



}
