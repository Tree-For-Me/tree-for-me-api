package com.TreeForMe.Models;

public class Plant {

    private String plantName;
    private String imageLink;
    private String botName;

    public Plant(String plantName, String imageLink, String botName) {
        this.plantName = plantName;
        this.imageLink = imageLink;
        this.botName = botName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}
