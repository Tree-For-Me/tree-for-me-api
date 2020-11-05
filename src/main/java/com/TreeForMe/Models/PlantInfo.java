package com.TreeForMe.Models;

public class PlantInfo {

    private String flowerType;
    private String light;
    private String flowers;
    private String humidity;

    public PlantInfo() {
        this.flowerType = "";
        this.light = "";
        this.flowers = "flowering";
        this.humidity = "";
    }

    public PlantInfo(String flowerType, String light, String flowers, String humidity) {
        this.flowerType = flowerType;
        this.light = light;
        this.flowers = flowers;
        this.humidity = humidity;
    }

    public String getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(String flowerType) {
        this.flowerType = flowerType;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}