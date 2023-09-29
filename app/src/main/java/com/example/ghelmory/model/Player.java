package com.example.ghelmory.model;


public class Player {
    private String name;
    private int score;

    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setScore(int score){
        this.score = score;
    }
}
