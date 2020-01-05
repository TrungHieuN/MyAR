package com.example.myar.RoomDatabase.Local;

import com.example.myar.RoomDatabase.DataSource.ICartDataSource;
import com.example.myar.RoomDatabase.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements ICartDataSource {

    private CartDAO cartDAO;
    private static CartDataSource instance;

    private CartDataSource(CartDAO cartDAO){
        this.cartDAO = cartDAO;
    }

    public static CartDataSource getInstance(CartDAO cartDAO){
        if(instance == null)
            instance = new CartDataSource(cartDAO);
        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItem() {
        return cartDAO.getCartItem();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int ItemId) {
        return cartDAO.getCartItemById(ItemId);
    }

    @Override
    public int countItem() {
        return cartDAO.countItem();
    }

    @Override
    public void emptyCart() {
        cartDAO.emptyItem();
    }

    @Override
    public void insertToCart(Cart... carts) {
        cartDAO.insertToCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
        cartDAO.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        cartDAO.deleteCartItem(cart);
    }
}

