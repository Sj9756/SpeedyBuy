package com.example.speedybuy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Items_description;
import com.example.speedybuy.R;
import com.example.speedybuy.database.Database_cart;
import com.example.speedybuy.key.Ikey;

import java.util.ArrayList;

class ViewHolderCart extends RecyclerView.ViewHolder {
    RelativeLayout layout_of_item_empty;
    LinearLayout linear_layout_of_intent,layout_of_item;
    ImageView cart_image_view;
    TextView cart_qty_text_view, cart_heading, cart_size, cart_price, cart_delivery_date, cart_delivery_type;
    RatingBar cart_rating_bar;
    AppCompatSpinner spinner;
    AppCompatButton cart_delete_btn;

    public ViewHolderCart(@NonNull View itemView) {
        super(itemView);
        linear_layout_of_intent = itemView.findViewById(R.id.linear_layout_of_intent);
        cart_qty_text_view = itemView.findViewById(R.id.cart_qty_text_view);
        cart_image_view = itemView.findViewById(R.id.cart_image_view);
        cart_heading = itemView.findViewById(R.id.cart_heading);
        cart_size = itemView.findViewById(R.id.cart_size);
        cart_price = itemView.findViewById(R.id.cart_price);
        cart_delivery_date = itemView.findViewById(R.id.cart_delivery_date);
        cart_delivery_type = itemView.findViewById(R.id.cart_delivery_type);
        cart_rating_bar = itemView.findViewById(R.id.cart_rating_bar);
        spinner = itemView.findViewById(R.id.spinner);
        cart_delete_btn = itemView.findViewById(R.id.cart_delete_btn);
        layout_of_item_empty=itemView.findViewById(R.id.layout_of_item_empty);
        layout_of_item=itemView.findViewById(R.id.layout_of_item);

    }
}

public class Cart_recycler_adapter extends RecyclerView.Adapter<ViewHolderCart> {
    String fragment_name;
    ArrayList<Integer> qty_array = new ArrayList<>();
    ArrayList<Items_list> items_array;
    Context context;

    public Cart_recycler_adapter(ArrayList<Items_list> items_array, Context context,String fragment_name) {
        this.items_array = items_array;
        this.context = context;
        qty_array.add(1);
        qty_array.add(2);
        qty_array.add(3);
        this.fragment_name=fragment_name;
    }

    @NonNull
    @Override
    public ViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_cart_item, parent, false);

        return new ViewHolderCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCart holder, int position) {
        if(items_array.isEmpty()){
            holder.layout_of_item.setVisibility(View.INVISIBLE);
            holder.layout_of_item_empty.setVisibility(View.VISIBLE);
        }
        else {
            ArrayAdapter<Integer> ad = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, qty_array);
            holder.spinner.setAdapter(ad);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int QTY = (int) parent.getItemAtPosition(position);
                    String qtya = "Qty " + QTY;
                    holder.cart_qty_text_view.setText(qtya);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            Items_list currentItem = items_array.get(position);
            holder.cart_heading.setText(currentItem.subheading);
            holder.cart_rating_bar.setRating(currentItem.setRating);
            holder.cart_price.setText(String.valueOf(currentItem.price));
            String url = currentItem.imageUrl;
            Glide.with(context).load(url).into(holder.cart_image_view);
            holder.cart_delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try (Database_cart cart = new Database_cart(context)) {
                        cart.deleteRecord(currentItem.id);
                        items_array.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }
            });
            holder.linear_layout_of_intent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, Items_description.class);
                    intent.putExtra(Ikey.FRAGMENT,fragment_name);
                    intent.putExtra(Ikey.POSITION,holder.getAdapterPosition());
                    intent.putExtra(Ikey.ID,currentItem.id);
                    intent.putExtra(Ikey.IMG,currentItem.imageUrl);
                    intent.putExtra(Ikey.HEADING,currentItem.heading);
                    intent.putExtra(Ikey.SUBHEADING,currentItem.subheading);
                    intent.putExtra(Ikey.PRICE,currentItem.price);
                    intent.putExtra(Ikey.RATING,currentItem.setRating);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

         return Math.max(1, items_array.size());
    }


}
