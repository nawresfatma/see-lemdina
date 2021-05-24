package com.example.bassametproject;

import java.util.Comparator;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private String image;
    private int image2;
    private int point;
    private int rank;

    public User(int image2, String name,int point, int rank) {

        this.image2 = image2;
        this.name = name;
        this.point = point;
        this.rank = rank;
    }

    public User(String name) {
        this.name = name;
    }

    public User(){

    }

    public User(String name,  String image) {
        this.name = name;
        this.image = image;
    }

    public User(String id, String name, String email, String password,String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public User(String id, String name, String email, String image, int point, int rank) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.point = point;
        this.rank = rank;
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

    public static void setPointSort(Comparator<User> pointSort) {
        User.pointSort = pointSort;
    }

    public static Comparator<User> pointSort = new Comparator<User>() {
        @Override
        public int compare(User u1, User u2) {
            return Float.compare(u1.point , u2.point);
        }
    };

}
