package com.example.bassametproject;

public class event {

private String eventName,eventLocation,eventPrice;
private String eventimg;

    public event() {
    }

    public event(String eventName, String eventLocation, String eventPrice, String eventimg) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventPrice = eventPrice;
        this.eventimg = eventimg;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventimg() {
        return eventimg;
    }

    public void setEventimg(String eventimg) {
        this.eventimg = eventimg;
    }
}
