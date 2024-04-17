package com.example.speedybuy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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

public class Adapter_home_fragment extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<Items_list> items;
    Context context;
    private ArrayList<Items_list> filteredItemList;

    public Adapter_home_fragment(Context context, ArrayList<Items_list> items) {
        this.items = items;
        this.filteredItemList = new ArrayList<>(items);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_items_fragment, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items_list currentItem = filteredItemList.get(position);
        holder.heading.setText(currentItem.heading);
        holder.subheading.setText(currentItem.subheading);
        String price="₹"+currentItem.price;
        holder.price.setText(price);
        holder.ratingBar.setRating(currentItem.setRating);
        String url = currentItem.imageUrl;
        Glide.with(context).load(url).into(holder.product);
        holder.grid_items.setOnClickListener(v -> {
            Intent intent =new Intent(context, Items_description.class);
            intent.putExtra(Ikey.FRAGMENT,holder.toString());
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
        return filteredItemList.size();
    }

}
