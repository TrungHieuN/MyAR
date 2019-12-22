package com.example.myar;

import android.net.Uri;

public class PlantItem{
    private int id;
    private String plantName;
    private String description;
    private String price;
    private Uri image;

    public PlantItem (int id, String plantName, String description, String price, Uri image){
        this.id = id;
        this.plantName = plantName;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getPlantName() {
        return plantName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public Uri getImage() {
        return image;
    }
}
