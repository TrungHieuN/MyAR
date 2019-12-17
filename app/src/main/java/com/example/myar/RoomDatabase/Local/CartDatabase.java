package com.example.myar.RoomDatabase.Local;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myar.RoomDatabase.ModelDB.Cart;

@Database(entities = {Cart.class}, version = 2, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract  CartDAO cartDAO();
    private static CartDatabase instance;

    public static CartDatabase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, CartDatabase.class, "PlantsDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;

    }
}
