package com.example.ghelmory.gameAdd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ghelmory.utilities.ImagePicker;
import com.example.ghelmory.R;
import com.example.ghelmory.databinding.FragmentAddGameBinding;

public class AddGameFragment extends Fragment {
    private FragmentAddGameBinding binding;
    private AddGameViewModel addGameViewModel;
    private final ImagePicker imagePicker = new ImagePicker();
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                addGameViewModel.setImageUrl(imagePicker.onImagePickedResult(result,getContext()));
            }
    );

    public static AddGameFragment newInstance() {
        return new AddGameFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addGameViewModel =
                new AddGameViewModel();
        binding = FragmentAddGameBinding.inflate(inflater, container, false);
        binding.validateAddGameButton.setOnClickListener(view -> saveGame());


        addGameViewModel.getImageUrlLiveData().observe(getViewLifecycleOwner(), imageUrl -> {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Uri imageUri = Uri.parse(imageUrl);
                binding.imageView.setImageURI(imageUri);
            } else {
                binding.imageView.setImageResource(R.drawable.baseline_image_24);
            }
        });
        setChooseImageButtonBinding();
        View root = binding.getRoot();
        return root;
    }

    private void saveGame(){
        String title = binding.titleEdit.getText().toString();
        String description = binding.descriptionEdit.getText().toString();
        String minPlayers = binding.minPlayerEdit.getText().toString();
        String maxPlayers = binding.maxPlayerEdit.getText().toString();
        String estimatedTime = binding.playtimeEdit.getText().toString();

        if(addGameViewModel.saveGame(title, description, minPlayers, maxPlayers, estimatedTime)){
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }else{
                Toast.makeText(getContext(),"Error: game not saved", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(),"Error: invalid input\nTitle and description must be filled, min and max player between 1 and 99 and estimated Time <10000", Toast.LENGTH_LONG).show();
        }
    }
    private void setChooseImageButtonBinding() {
        binding.chooseImageButton
                .setOnClickListener(view -> {
                    imagePicker.runImagePicker(requireContext(),requireActivity(), imagePickerLauncher);
                });
    }

}
