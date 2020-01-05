package com.example.myar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myar.RoomDatabase.ModelDB.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context context;
    private List<Cart> cartList;

    CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Picasso.get()
                .load(cartList.get(position).image)
                .into(holder.img_product);

        holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
        holder.txt_product_name.setText(cartList.get(position).name);
        holder.txt_price.setText(cartList.get(position).price);

        //Auto save item when user change amount
        holder.txt_amount.setOnValueChangeListener((view, oldValue, newValue) -> {
            Cart cart = cartList.get(position);
            cart.amount = newValue;

            MainActivity.cartRepository.updateCart(cart);
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_product_name, txt_price;
        ElegantNumberButton txt_amount;

        RelativeLayout view_background;
        LinearLayout view_foreground;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.cart_item_image);
            txt_product_name = itemView.findViewById(R.id.cart_item_name);
            txt_price = itemView.findViewById(R.id.cart_item_price);
            txt_amount = itemView.findViewById(R.id.cart_item_amount);

            view_background = itemView.findViewById(R.id.cart_item_background);
            view_foreground = itemView.findViewById(R.id.cart_item_foreground);
        }
    }
    public void removeItem(int position)
    {
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position)
    {
        cartList.add(position, item);
        notifyItemInserted(position);
    }
}

