package com.example.myar.RoomDatabase.ModelDB;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlantsCart")
public class Cart  {

@NonNull
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="Image")
    public String image;

    @ColumnInfo(name="Amount")
    public int amount;

    @ColumnInfo(name = "plantName")
    public String name;

    @ColumnInfo(name="Description")
    public String description;

    @ColumnInfo(name="Price")
    public String price;
}


