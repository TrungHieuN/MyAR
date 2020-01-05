package com.example.myar;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myar.RoomDatabase.ModelDB.Cart;
import com.google.gson.*;
import com.squareup.picasso.Picasso;

public class ProductViewActivity extends FragmentActivity {
        Toolbar productToolbar;
        ImageView productImage;
        TextView productName;
        TextView productDesc;
        TextView productPrice;

        String imageHolder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.productview_activity);

            productToolbar = findViewById(R.id.toolbarTop);
            productImage = findViewById(R.id.product_image);
            productName = findViewById(R.id.product_name);
            productDesc = findViewById(R.id.product_description);
            productPrice = findViewById(R.id.product_price);

            String nameHolder = getIntent().getStringExtra("item Names");
            productName.setText(nameHolder);

            imageHolder = getIntent().getExtras().getString("item Images");
            Picasso.get().load(imageHolder).into(productImage);

            String descHolder = getIntent().getStringExtra("item Desc");
            productDesc.setText(descHolder);

            String priceHolder = getIntent().getStringExtra("item Price");
            productPrice.setText(priceHolder);

            productToolbar.setTitle("Detail");
            setActionBar(productToolbar);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProductViewActivity.this, R.color.colorProductBackground));

            //backButton as arrow
            if (getActionBar() != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setDisplayShowHomeEnabled(true);
            }

            Button previewButton = findViewById(R.id.previewButton);
            previewButton.setOnClickListener(view -> openPreview());

            Button addToCartButton = findViewById(R.id.addToCartButton);
            addToCartButton.setOnClickListener(view -> addToCartActivity());
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish(); // close this activity and return to last activity
            }
            return super.onOptionsItemSelected(item);
        }
    public void openPreview (){

        Intent intent = new Intent(this, ArFragmentPreview.class);
        startActivity(intent);
    }

    public void addToCartActivity (){

        try {
            Cart cartItem = new Cart();
            cartItem.name = productName.getText().toString();
            cartItem.amount = 1;
          //  cartItem.description = productDesc.getText().toString();
            cartItem.price = productPrice.getText().toString();
            cartItem.image = getIntent().getExtras().getString("item Images");

            MainActivity.cartRepository.insertToCart(cartItem);
            Log.d("MyAR", new Gson().toJson(cartItem));
            Toast.makeText(this, "Save Item to Cart successful", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
