package com.example.bassametproject;

import java.util.Comparator;

public class RatingComment {
    private String name;
    private String title;
    private String description;
    private Float rating;
    private String image;

    public RatingComment() {
    }

    public RatingComment(String name, String title, String description, Float rating, String image) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.rating = rating;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static void setRateingSort(Comparator<RatingComment> rateingSort) {
        com.example.bassametproject.RatingComment.rateingSort = rateingSort;
    }

    public static Comparator<RatingComment> rateingSort = new Comparator<RatingComment>() {
        @Override
        public int compare(com.example.bassametproject.RatingComment b1, com.example.bassametproject.RatingComment b2) {
            return Float.compare(b1.rating , b2.rating);
        }
    };

}
