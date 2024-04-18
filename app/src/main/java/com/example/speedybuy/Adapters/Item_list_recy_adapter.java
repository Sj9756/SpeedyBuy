package com.example.speedybuy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Items_description;
import com.example.speedybuy.R;
import com.example.speedybuy.key.Ikey;

import java.util.ArrayList;

class ViewHolder extends RecyclerView.ViewHolder {
    GridLayout grid_items;
    ImageView product;
    TextView heading, subheading, price;
    RatingBar ratingBar;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        product = itemView.findViewById(R.id.image_items);
        heading = itemView.findViewById(R.id.heading_items);
        subheading = itemView.findViewById(R.id.sub_heading_items);
        price = itemView.findViewById(R.id.price_items);
        ratingBar = itemView.findViewById(R.id.rating_items);
        grid_items=itemView.findViewById(R.id.grid_items);

    }
}

public class Item_list_recy_adapter extends RecyclerView.Adapter<ViewHolder> {
   public ArrayList<Items_list> items;
    Context context;
    String fragment_name;

    public Item_list_recy_adapter(Context context, ArrayList<Items_list> items,String fragment_name) {
        this.items = items;
        this.context = context;
        this.fragment_name=fragment_name;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_items_fragment, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items_list currentItem = items.get(position);
        holder.heading.setText(currentItem.heading);
        holder.subheading.setText(currentItem.subheading);
        String price="₹"+currentItem.price;
        holder.price.setText(price);
        holder.ratingBar.setRating(currentItem.setRating);
        String url = currentItem.imageUrl;
        Glide.with(context).load(url).into(holder.product);
        holder.grid_items.setOnClickListener(v -> {
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
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
