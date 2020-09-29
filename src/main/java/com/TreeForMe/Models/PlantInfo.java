package com.TreeForMe.Models;

import org.springframework.web.bind.annotation.GetMapping;

public class PlantInfo {

    private String flowerType;
    private String light;
    private boolean flowers;
    private boolean humidity;

    public PlantInfo(String messageContent, String user) {
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

    public boolean isFlowers() {
        return flowers;
    }

    public void setFlowers(boolean flowers) {
        this.flowers = flowers;
    }

    public boolean isHumidity() {
        return humidity;
    }

    public void setHumidity(boolean humidity) {
        this.humidity = humidity;
    }
}