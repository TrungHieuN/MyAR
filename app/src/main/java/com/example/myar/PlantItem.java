package com.example.myar;

import android.net.Uri;

public class PlantItem{
    private int id;
    private String plantName;
    private String description;
    private String price;
    private Uri image;

    public PlantItem(){}

    public PlantItem(int id, String plantName, String description, String price, Uri image){
        this.id = id;
        this.plantName = plantName;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
