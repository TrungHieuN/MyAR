package com.example.myar.RoomDatabase.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myar.RoomDatabase.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM PlantsCart")
        Flowable<List<Cart>> getCartItem();

    @Query("SELECT * FROM PlantsCart WHERE id=:ItemId")
    Flowable<List<Cart>>getCartItemById(int ItemId);

    @Query("SELECT COUNT(*) FROM PlantsCart")
    int countItem();

    @Query("DELETE FROM PlantsCart")
    void emptyItem();

    @Insert
    void insertToCart(Cart...carts);

    @Update
    void updateCart(Cart...carts);

    @Delete
    void deleteCartItem(Cart cart);
}

