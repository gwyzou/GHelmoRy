package com.example.ghelmory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ghelmory.databinding.ActivityMainBinding;
import com.example.ghelmory.gameInstanceDetails.GameInstanceDetailsFragment;
import com.example.ghelmory.gameInstanceList.ISelectGameInstance;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ISelectGameInstance {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_addchoice,R.id.navigation_stats)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

        @Override
        public void onSelectedGameInstance(UUID gameInstanceId) {
            getSupportFragmentManager().beginTransaction().replace(R.id.list, GameInstanceDetailsFragment.newInstance(gameInstanceId)).addToBackStack(null).commit();
        }

}