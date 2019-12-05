package com.example.myar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;


public class ProductViewActivity extends AppCompatActivity {
    Toolbar productToolbar;
    ImageView productImage;

    private String[] names = {"name1", "name2", "name3", "name4", "name5", "name6", "name7" };
    private int[] images = {R.drawable.background, R.drawable.ic_launcher_background, R.drawable.background,
            R.drawable.background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.background};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);

        productToolbar = findViewById(R.id.toolbarTop);
        productImage = findViewById(R.id.product_image);

        //Back Button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> this.finish());

        //itemName to Toolbar - Not yet finish!!!
        Bundle bundle = getIntent().getExtras();
        productToolbar.setTitle(bundle.getString(Arrays.toString(this.names)));

        Button previewButton = findViewById(R.id.previewButton);
        previewButton.setOnClickListener(v -> openPreview());
    }
    public void openPreview (){
        Intent intent = new Intent(this, ArFragmentPreview.class);
        startActivity(intent);
    }
}