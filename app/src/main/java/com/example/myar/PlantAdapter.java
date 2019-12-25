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

import com.squareup.picasso.Picasso;

import java.util.List;

class PlantAdapter extends ArrayAdapter<PlantItem> {

    private Activity activity;
    private List<PlantItem> plantlist;

    public PlantAdapter(@NonNull Activity activity, List<PlantItem> plantlist) {
        super(activity, R.layout.layout_list_item, plantlist);
        this.activity = activity;
        this.plantlist = plantlist;
    }

    public class customadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return plantlist.size();
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

            PlantItem plantItem = plantlist.get(position);

            tv.setText(plantItem.getPlantName());
            Picasso.get().load(plantItem.getImage()).into(image);
            pv.setText(plantItem.getPrice());
            return listItemView;
        }
    }
}

