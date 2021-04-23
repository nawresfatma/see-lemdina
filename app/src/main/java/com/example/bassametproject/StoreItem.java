package com.example.bassametproject;

public class StoreItem {
    private String storeImage;
    private String storeName;
    private String storeDescription;

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
}
