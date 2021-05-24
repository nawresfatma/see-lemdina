package com.example.bassametproject;

public class StoreItem {
    private String storeImage;
    private String storeName;
    private String storeDescription;
    private String storeDescriptionShort;
    private String id ;
    private float storeRate;
    private String storeLocation;
    private String openingHour;
    private float longitude ,latitude;
    private int type;



    public StoreItem(String storeImage, String storeName, String storeDescription, String id, float storeRate, String storeLocation, String openingHour, float longitude, float latitude) {
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

    public String getStoreDescriptionShort() {
        return storeDescriptionShort;
    }

    public void setStoreDescriptionShort(String storeDescriptionShort) {
        this.storeDescriptionShort = storeDescriptionShort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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