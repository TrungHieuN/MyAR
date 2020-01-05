package com.example.myar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductFragment extends Fragment {

    private ListView listView;
    private NotificationBadge badge;
    List<PlantItem> plantlist;

    ArrayAdapter<PlantItem> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        plantlist = new ArrayList<>();

        View view = inflater.inflate(R.layout.product_fragment, container, false);
        PlantAdapter plantAdapter = new PlantAdapter(getActivity(), plantlist);
        PlantAdapter.customadapter ca = plantAdapter.new customadapter();
        listView = view.findViewById(R.id.ItemListView);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            PlantItem plantItem = plantlist.get(position);

            String nameItemListview = plantItem.getPlantName();
            Uri imageItemListview = plantItem.getImage();
            String descItemListview = plantItem.getDescription();
            String priceItemListview = plantItem.getPrice();

            Intent intent = new Intent(view1.getContext(), ProductViewActivity.class);

            intent.putExtra("item Names", nameItemListview);
            intent.putExtra("item Images", imageItemListview.toString());
            intent.putExtra("item Desc", descItemListview);
            intent.putExtra("item Price", priceItemListview);
            ProductFragment.this.startActivity(intent);

        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Plants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantlist.clear();
              for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()) {
                   PlantItem plantItem = plantSnapshot.getValue(PlantItem.class);
                   plantlist.add(plantItem);
              }
                adapter = new ArrayAdapter<>(getContext(), R.layout.product_item_layout,
                                                R.id.item_name, plantlist);
                listView.setAdapter(adapter);
                listView.setAdapter(ca);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        return view;
    }


    @Override
        public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);
        View view = menu.findItem(R.id.action_cart).getActionView();;
        badge = view.findViewById(R.id.badge);
        ImageView cart_icon = view.findViewById(R.id.cart_icon);
        cart_icon.setOnClickListener(v -> startActivity(new Intent(
                               getContext(), CartActivity.class)));
        updateCartCount();
    }

    private void updateCartCount() {
        if(badge == null) return;
        getActivity().runOnUiThread(() -> {
            if (MainActivity.cartRepository.countItem() == 0)
                badge.setVisibility(View.INVISIBLE);
            else {
                badge.setVisibility((View.VISIBLE));
                badge.setText(String.valueOf(MainActivity.cartRepository.countItem()));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartCount();
    }
}
