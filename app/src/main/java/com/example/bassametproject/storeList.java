package com.example.bassametproject;

public class storeList {

    private String prName;
    private String prPrice ;
    private String prImg;
    private int category;

    public storeList() {
    }

    public storeList(String prName, String prPrice, String prImg, int category) {
        this.prName = prName;
        this.prPrice = prPrice;
        this.prImg = prImg;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(String prPrice) {
        this.prPrice = prPrice;
    }

    public String getPrImg() {
        return prImg;
    }

    public void setPrImg(String prImg) {
        this.prImg = prImg;
    }
}
