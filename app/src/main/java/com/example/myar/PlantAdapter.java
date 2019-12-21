package com.example.myar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PlantAdapter extends ArrayAdapter<PlantItem> {

    private Activity activity;
    private List<PlantItem> plantlist;

    private String[] names = {"name1", "name2", "name3", "name4", "name5", "name6", "name7" };
    private int[] images = {R.drawable.background, R.drawable.ic_launcher_background, R.drawable.background,
            R.drawable.background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.background};
    private String[] description ={"123", "456", "789", "1011", "abc", "3311", "31313"};
    private String[] price ={"19,00 €", "20,50 €", "35,00 €", "44,19 €", "5,79 €", "89,99 €", "1,99 €"};

    PlantAdapter(@NonNull Activity activity, List<PlantItem> plantlist) {
        super(activity, R.layout.layout_list_item, plantlist);
        this.activity = activity;
        this.plantlist = plantlist;
    }
    public class customadapter extends BaseAdapter {

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
            LayoutInflater inflater = activity.getLayoutInflater();
            listItemView = inflater.inflate(R.layout.layout_list_item, null, true);

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
}
