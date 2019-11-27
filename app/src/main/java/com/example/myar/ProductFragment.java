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

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ProductFragment extends Fragment {

    private String[] names = {"name1", "name2", "name3", "name4", "name5", "name6", "name7" };
    private int[] images = {R.drawable.background, R.drawable.ic_launcher_background, R.drawable.background,
            R.drawable.background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.background};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        ListView listView = view.findViewById(R.id.ListView);
        ProductFragment.customadapter ca = new ProductFragment.customadapter();

        listView.setOnItemClickListener((parent, view1, position, id) -> {

            // Test
          /*  if (position == 0) {
                Toast.makeText(view1.getContext(), names[position] + "selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view1.getContext(), names[position] + "selected", Toast.LENGTH_SHORT).show();
            }*/

            Intent intent = new Intent(view1.getContext(), ProductViewActivity.class);
            intent.putExtra(names[position], (Integer) listView.getItemAtPosition(position));
            ProductFragment.this.startActivity(intent);
        });
        listView.setAdapter(ca);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_status, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
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
            tv.setText(names[position]);
            image.setImageResource(images[position]);
            return view;
        }

    }

}
