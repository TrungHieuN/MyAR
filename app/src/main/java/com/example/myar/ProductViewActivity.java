package com.example.myar;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myar.Database.ModelDB.Cart;
import com.google.gson.*;


public class ProductViewActivity extends AppCompatActivity {
    Toolbar productToolbar;
    ImageView productImage;
    TextView productName;
    TextView productDesc;
    TextView productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);

        productToolbar = findViewById(R.id.toolbarTop);
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productDesc = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);

        String nameHolder = getIntent().getStringExtra("item Names");
        productName.setText(nameHolder);

        int imageHolder = getIntent().getIntExtra("item Images", -1);
        productImage.setImageResource(imageHolder);

        String descHolder = getIntent().getStringExtra("item Desc");
        productDesc.setText(descHolder);

        String priceHolder = getIntent().getStringExtra("item Price");
        productPrice.setText(priceHolder);

        productToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(productToolbar);
        getWindow().setStatusBarColor(ContextCompat.getColor(ProductViewActivity.this, R.color.colorProductBackground));

        //backButton as arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button previewButton = findViewById(R.id.previewButton);
        previewButton.setOnClickListener(view -> openPreview());

        Button addToCartButton = findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(view -> addToCartActivity());
    }
    //click on Arrow to go back to last Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public void openPreview (){

        Intent intent = new Intent(this, ArFragmentPreview.class);
        startActivity(intent);
    }

    public void addToCartActivity (){

     //   Intent intent = new Intent(this, CartActivity.class);
      //  startActivity(intent);

        try {
            Cart cartItem = new Cart();
            cartItem.name = productName.getText().toString();
            cartItem.description = productDesc.getText().toString();
            cartItem.price = productPrice.getText().toString();

            MainActivity.cartRepository.insertToCart(cartItem);
            Log.d("MyAR", new Gson().toJson(cartItem));
            Toast.makeText(this, "Save Item to Cart successful", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

      /*  AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cart_layout,null);*/
    }

}
