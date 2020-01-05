package com.example.myar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myar.RoomDatabase.ModelDB.Cart;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

import static com.google.android.material.snackbar.Snackbar.*;


public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    private RelativeLayout rootLayout;
    private RecyclerView recycler_cart;
    private CartAdapter cartAdapter;
    private CompositeDisposable compositeDisposable;
    private List<Cart> cartList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        Toolbar cartToolbar = findViewById(R.id.CartToolbar);
        cartToolbar.setTitle("Cart");
        cartToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(cartToolbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(CartActivity.this, R.color.colorProductBackground));

        compositeDisposable = new CompositeDisposable();

        rootLayout = findViewById(R.id.rootLayout);

        recycler_cart = findViewById(R.id.cartListView);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecycleItemTouchHelper(0, ItemTouchHelper.LEFT , this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);

        loadCartItems();

        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Proceed to Checkout", Toast.LENGTH_SHORT)
                .show());

        //backButton as arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;// close this activity and
                        // return to previous activity
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
        cartList = carts;
        cartAdapter = new CartAdapter(this, carts);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            // if (cartList != null && cartList.size() != 0)
            String name = cartList.get(viewHolder.getAdapterPosition()).name;
            Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
            int deletedIndex = viewHolder.getAdapterPosition();

            //Delete Item from Adapter
            cartAdapter.removeItem(deletedIndex);
            //Delete Item from Room database
            MainActivity.cartRepository.deleteCartItem(deletedItem);

            Snackbar snackbar = make(rootLayout, name + " removed from Cart List",
                                        Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", v -> {
                cartAdapter.restoreItem(deletedItem, deletedIndex);
                MainActivity.cartRepository.insertToCart(deletedItem);
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}
