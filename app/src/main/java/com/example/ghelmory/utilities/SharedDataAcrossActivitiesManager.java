package com.example.ghelmory.utilities;

public class SharedDataAcrossActivitiesManager {
    private static SharedDataAcrossActivitiesManager instance = null;
    private String sharedData = null;

    private SharedDataAcrossActivitiesManager() {
    }
    public static synchronized SharedDataAcrossActivitiesManager getInstance() {
        if (instance == null) {
            instance = new SharedDataAcrossActivitiesManager();
        }
        return instance;
    }

    public void setSharedData(String data) {
        this.sharedData = data;
    }

    public String getSharedData() {
        return sharedData;
    }
}
