package com.example.bassametproject;

public class ListClassement {
    private int rank;
    private String name;
    private int points;
    private int coin;
    private int profile;

    public ListClassement(int rank, String name, int points, int coin, int profile) {
        this.rank = rank;
        this.name = name;
        this.points = points;
        this.coin = coin;
        this.profile = profile;
    }

//commit

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }


}

