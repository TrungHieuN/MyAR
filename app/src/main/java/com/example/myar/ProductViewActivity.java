package com.example.myar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


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

        int imageHolder = getIntent().getIntExtra("item Images",-1);
        productImage.setImageResource(imageHolder);

        String descHolder = getIntent().getStringExtra("item Desc");
        productDesc.setText(descHolder);

        String priceHolder = getIntent().getStringExtra("item Price");
        productPrice.setText(priceHolder);

        productToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(productToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button previewButton = findViewById(R.id.previewButton);
        previewButton.setOnClickListener(view -> openPreview());

        Button buyButton = findViewById(R.id.buyButton);
        buyButton.setOnClickListener(view -> openBuyActivity());

;
    }
    //click on Arrow to go back to MainActivity
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

    public void openBuyActivity (){
        Intent intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }

}
