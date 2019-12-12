package com.example.myar.Database.DataSource;

import com.example.myar.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {
    Flowable<List<Cart>> getItem();
    Flowable<List<Cart>>getCartItemById(int ItemId);
    int countItem();
    void emptyCart();
    void insertToCart(Cart...carts);
    void updateCart(Cart...carts);
    void deleteCartItem(Cart cart);
}
