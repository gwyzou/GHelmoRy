package com.example.ghelmory;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ghelmory.databinding.ActivitySubBinding;
import com.example.ghelmory.gameAdd.AddGameFragment;
import com.example.ghelmory.gameInstanceDetails.GameInstanceDetailsFragment;
import com.example.ghelmory.gameInstanceLocation.LocationFragment;

import java.util.UUID;

public class SubActivity extends AppCompatActivity {

    ActivitySubBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String fragmentType = getIntent().getStringExtra("fragmentType");
        String gameInstanceId = getIntent().getStringExtra("gameInstanceId");


        if(AddGameFragment.class.getSimpleName().equals(fragmentType)){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AddGameFragment.newInstance()).commit();

        } else if (GameInstanceDetailsFragment.class.getSimpleName().equals(fragmentType) && gameInstanceId != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, GameInstanceDetailsFragment.newInstance(UUID.fromString(gameInstanceId))).commit();

        }else if(LocationFragment.class.getSimpleName().equals(fragmentType)){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LocationFragment.newInstance()).commit();

        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}
