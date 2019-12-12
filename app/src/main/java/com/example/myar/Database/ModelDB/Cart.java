package com.example.myar.Database.ModelDB;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;


@Entity(tableName = "PlantsCart")
public class Cart {

@NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

   // @ColumnInfo(name="Image")
    //public Blob image;

    @ColumnInfo(name = "plantName")
    public String name;

    @ColumnInfo(name="Description")
    public String description;

    @ColumnInfo(name="Price")
    public String price;

}
