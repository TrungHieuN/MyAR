package com.example.myar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myar.RoomDatabase.ModelDB.Cart;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recycler_cart;
    private Button checkoutButton;
    private CompositeDisposable compositeDisposable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        Toolbar cartToolbar = findViewById(R.id.CartToolbar);
        cartToolbar.setTitle("Cart");
        cartToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(cartToolbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(CartActivity.this, R.color.colorProductBackground));

        compositeDisposable = new CompositeDisposable();

        recycler_cart = findViewById(R.id.cartListView);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        checkoutButton = findViewById(R.id.checkout_button);

        loadCartItems();

        //backButton as arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;// close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCartItems() {
        compositeDisposable.add(
                MainActivity.cartRepository.getCartItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::displayCartItem)
        );
    }

    private void displayCartItem(List<Cart> carts) {
        CartAdapter cartAdapter = new CartAdapter(this, carts);
        recycler_cart.setAdapter(cartAdapter);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
