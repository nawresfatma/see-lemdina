package com.example.bassametproject;

import java.util.Comparator;

public class User {

    public static Comparator<? super User> pointSort;
    private String id;
    private String name;
    private String email;
    private String password;
    private String image;
    private int point;
    private int rank;


    public User(String name) {
        this.name = name;
    }

    public User(){

    }

    public User(String name,  String image) {
        this.name = name;
        this.image = image;
    }

    public User(String id, String name, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
