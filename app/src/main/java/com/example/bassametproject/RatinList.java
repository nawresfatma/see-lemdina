package com.example.bassametproject;

import java.util.Comparator;

public class RatinList {
    private String name;
    private String title;
    private String description;
    private Float storeRate;
    private String image;


    public RatinList() {
    }

    public RatinList(String name, String title, String description, Float rating, String image) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.storeRate = rating;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getStoreRate() {
        return storeRate;
    }

    public void setStoreRate(Float storeRate) {
        this.storeRate = storeRate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static void setRateingSort(Comparator<RatinList> rateingSort) {
        RatinList.rateingSort = rateingSort;
    }

    public static Comparator<RatinList> rateingSort = new Comparator<RatinList>() {
        @Override
        public int compare(RatinList b1, RatinList b2) {
            return Float.compare(b1.storeRate , b2.storeRate);
        }
    };

}
