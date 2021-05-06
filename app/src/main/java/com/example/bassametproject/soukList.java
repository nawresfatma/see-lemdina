package com.example.bassametproject;

public class soukList {
    private String soukName;
    private String vidioUrl;

    public soukList(String soukName, String vidioUrl) {
        this.soukName = soukName;
        this.vidioUrl = vidioUrl;
    }

    public soukList() {
    }

    public String getSoukName() {
        return soukName;
    }

    public void setSoukName(String soukName) {
        this.soukName = soukName;
    }

    public String getVidioUrl() {
        return vidioUrl;
    }

    public void setVidioUrl(String vidioUrl) {
        this.vidioUrl = vidioUrl;
    }
}
