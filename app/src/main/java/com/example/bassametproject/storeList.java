package com.example.bassametproject;

public class storeList {

    private String prName;
    private String prPrice ;
    private String prImg;

    public storeList() {
    }

    public storeList(String prName, String prPrice, String prImg) {
        this.prName = prName;
        this.prPrice = prPrice;
        this.prImg = prImg;
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
