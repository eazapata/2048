package com.example.a2048.DataBase;


public class Score{

    private String player;
    private int playerScore;
    private int id;
    private String time;

    public Score() {
    }

    public Score(int id,String player, int playerScore,String time) {
        this.player = player;
        this.playerScore = playerScore;
        this.id = id;
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String country) {
        this.time = country;
    }


}
