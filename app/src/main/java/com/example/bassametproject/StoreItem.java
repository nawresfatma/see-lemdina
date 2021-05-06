package com.example.bassametproject;

public class StoreItem {
    private String storeImage;
    private String storeName;
    private String storeDescription;
    private int id ;
    private float storeRate;
    private String storeLocation;
    private String openingHour;
    private float longitude ,latitude;

    public StoreItem(String storeImage, String storeName, String storeDescription, int id, float storeRate, String storeLocation, String openingHour, float longitude, float latitude) {
        this.storeImage = storeImage;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.id = id;
        this.storeRate = storeRate;
        this.storeLocation = storeLocation;
        this.openingHour = openingHour;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public StoreItem(String storeImage, String storeName, String storeDescription) {
        this.storeImage = storeImage;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
    }
    public StoreItem() {

    }



    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getStoreRate() {
        return storeRate;
    }

    public void setStoreRate(float storeRate) {
        this.storeRate = storeRate;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
