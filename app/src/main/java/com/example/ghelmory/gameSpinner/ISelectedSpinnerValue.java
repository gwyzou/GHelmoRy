package com.example.ghelmory.gameSpinner;

import androidx.lifecycle.LiveData;

import java.util.UUID;

public interface ISelectedSpinnerValue {
    LiveData<UUID> getSelectedGame();
}
