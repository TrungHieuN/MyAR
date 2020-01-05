package com.example.myar.RoomDatabase.DataSource;

import com.example.myar.RoomDatabase.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItem();
    Flowable<List<Cart>>getCartItemById(int ItemId);
    int countItem();
    void emptyCart();
    void insertToCart(Cart...carts);
    void updateCart(Cart...carts);
    void deleteCartItem(Cart cart);
}

