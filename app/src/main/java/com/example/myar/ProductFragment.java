package com.example.myar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


public class ProductFragment extends Fragment {

    private DatabaseReference databaseReference;

    private List<PlantItem> plantlist;
    private ListView listView;
    private NotificationBadge badge;

    private String[] names = {"name1", "name2", "name3", "name4", "name5", "name6", "name7" };
    private int[] images = {R.drawable.background, R.drawable.ic_launcher_background, R.drawable.background,
            R.drawable.background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.background};
    private String[] description ={"123", "456", "789", "1011", "abc", "3311", "31313"};
    private String[] price ={"19,00 €", "20,50 €", "35,00 €", "44,19 €", "5,79 €", "89,99 €", "1,99 €"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("plantItem");
        plantlist = new ArrayList<>();

        View view = inflater.inflate(R.layout.product_fragment, container, false);
        listView = view.findViewById(R.id.ItemListView);
        ProductFragment.customadapter ca = new ProductFragment.customadapter();
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            String nameItemListview = names[position];
            int imageItemListview = images[position];
            String descItemListview = description[position];
            String priceItemListview = price[position];

            Intent intent = new Intent(view1.getContext(), ProductViewActivity.class);

            intent.putExtra("item Names", nameItemListview);
            intent.putExtra("item Images", imageItemListview);
            intent.putExtra("item Desc", descItemListview);
            intent.putExtra("item Price", priceItemListview);
            ProductFragment.this.startActivity(intent);
        });

        listView.setAdapter(ca);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantlist.clear();
                for (DataSnapshot plantSnapshot : dataSnapshot.getChildren()){
                    PlantItem plantItem = plantSnapshot.getValue(PlantItem.class);

                    plantlist.add(plantItem);
                }

                PlantAdapter adapter = new PlantAdapter(getActivity(),plantlist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
        public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);
        View view = menu.findItem(R.id.action_cart).getActionView();;
        badge = view.findViewById(R.id.badge);
        ImageView cart_icon = view.findViewById(R.id.cart_icon);
        cart_icon.setOnClickListener(v -> startActivity(new Intent (getContext(), CartActivity.class)));
        updateCartCount();
    }

    private void updateCartCount() {
        if(badge == null) return;
        getActivity().runOnUiThread(() -> {
            if (MainActivity.cartRepository.countItem() == 0)
                badge.setVisibility(View.VISIBLE);
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
    class customadapter extends BaseAdapter {

           @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(final int position, View listItemView, ViewGroup parent) {
            listItemView = getLayoutInflater().inflate(R.layout.layout_list_item, null, true);


            TextView tv = listItemView.findViewById(R.id.item_name);
            ImageView image = listItemView.findViewById(R.id.item_image);
            TextView pv = listItemView.findViewById(R.id.item_price);

            if(plantlist!= null && plantlist.size() !=0) {
                PlantItem plantItem = plantlist.get(position);

                tv.setText(plantItem.getPlantName());
                image.setImageURI(plantItem.getImage());
                pv.setText(plantItem.getPrice());
            }
            return listItemView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartCount();
    }
}
