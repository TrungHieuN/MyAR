package com.example.myar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nex3z.notificationbadge.NotificationBadge;


public class ProductFragment extends Fragment {

    private NotificationBadge badge;

    private String[] names = {"name1", "name2", "name3", "name4", "name5", "name6", "name7" };
    private int[] images = {R.drawable.background, R.drawable.ic_launcher_background, R.drawable.background,
            R.drawable.background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.background};
    private String[] description ={"123", "456", "789", "1011", "abc", "3311", "31313"};
    private String[] price ={"19,00 €", "20,50 €", "35,00 €", "44,19 €", "5,79 €", "89,99 €", "1,99 €"};
    private String[] object3D = {"ArcticFox_Posed.sfb", "AJ-Vase.sfb", "10432_Aloe_Plant_v1_max2008_it2.sfb","AJ-Vase.sfb",
            "AJ-Vase.sfb", "ArcticFox_Posed.sfb", "10432_Aloe_Plant_v1_max2008_it2.sfb"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.product_fragment, container, false);
        ListView listView = view.findViewById(R.id.ItemListView);
        ProductFragment.customadapter ca = new ProductFragment.customadapter();
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            String nameItemListview = names[position];
            int imageItemListview = images[position];
            String descItemListview = description[position];
            String priceItemListview = price[position];
        //    String object3DListview = object3D[position];

            Intent intent = new Intent(view1.getContext(), ProductViewActivity.class);
            intent.putExtra("item Names", nameItemListview);
            intent.putExtra("item Images", imageItemListview);
            intent.putExtra("item Desc", descItemListview);
            intent.putExtra("item Price", priceItemListview);
         //   intent.putExtra("3D Object", object3DListview);
            ProductFragment.this.startActivity(intent);
        });

        listView.setAdapter(ca);
        return view;

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
        public View getView(final int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.layout_list_item, null);

            TextView tv = view.findViewById(R.id.item_name);
            ImageView image = view.findViewById(R.id.item_image);
            TextView pv = view.findViewById(R.id.item_price);

            tv.setText(names[position]);
            image.setImageResource(images[position]);
            pv.setText(price[position]);

            return view;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartCount();
    }
}
