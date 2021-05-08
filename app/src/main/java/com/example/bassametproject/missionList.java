package com.example.bassametproject;

public class missionList {
    private String title;
    private String desc;
    private int image;
    private String discover;

    public missionList() {
    }

    public missionList(String title, String desc, int image, String discover) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.discover = discover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDiscover() {
        return discover;
    }

    public void setDiscover(String discover) {
        this.discover = discover;
    }
}
