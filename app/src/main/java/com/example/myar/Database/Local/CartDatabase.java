package com.example.myar.Database.Local;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myar.Database.ModelDB.Cart;
import com.example.myar.ProductFragment;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public static CartDatabase getInstance(ProductFragment productFragment) {
        return null;
    }

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
