package com.example.myar.Database.DataSource;

import com.example.myar.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository implements ICartDataSource {

    private ICartDataSource iCartDataSource;

    private CartRepository(ICartDataSource iCartDataSource) {
        this.iCartDataSource = iCartDataSource;
    }

    private static CartRepository instance;

    public static CartRepository getInstance(ICartDataSource iCartDataSource){
        if(instance == null)
            instance = new CartRepository(iCartDataSource);
        return instance;

    }

    @Override
    public Flowable<List<Cart>> getItem() {
        return iCartDataSource.getItem();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int ItemId) {
        return iCartDataSource.getCartItemById(ItemId);
    }

    @Override
    public int countItem() {
        return iCartDataSource.countItem();
    }

    @Override
    public void emptyCart() {
        iCartDataSource.emptyCart();
    }

    @Override
    public void insertToCart(Cart... carts) {
        iCartDataSource.insertToCart(carts);
}

    @Override
    public void updateCart(Cart... carts) {
        iCartDataSource.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        iCartDataSource.deleteCartItem(cart);
    }
}
