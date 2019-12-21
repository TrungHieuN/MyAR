package com.example.myar.MainDatabase;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataRetrieve extends AppCompatActivity {

 /*   DatabaseReference dbreff;
    TextView item_name = findViewById(R.id.item_name);
    ImageView item_image = findViewById(R.id.item_image);
    TextView item_price = findViewById(R.id.item_price);
    TextView item_desc = findViewById(R.id.product_description);


    public void setDbreff(DatabaseReference dbreff) {
        this.dbreff = dbreff;
        dbreff = FirebaseDatabase.getInstance().getReference().child("Plants");
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("plantName").getValue().toString();
                Uri image = dataSnapshot.child("image").getValue().Uri;
                String description = dataSnapshot.child("description").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

                item_name.setText(name);
                item_image.setImageResource(image);
                item_desc.setText(description);
                item_price.setText(price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    } */
}
