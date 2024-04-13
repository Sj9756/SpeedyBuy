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
import com.example.speedybuy.Items_discription;
import com.example.speedybuy.R;
import com.example.speedybuy.database.Database_Op;

import java.util.ArrayList;
import java.util.Stack;

class ViewHolderWish extends RecyclerView.ViewHolder {
    GridLayout grid_items;
    ImageView product;
    TextView heading, subheading, price;
    RatingBar ratingBar;
    public ViewHolderWish(@NonNull View itemView) {
        super(itemView);
        product = itemView.findViewById(R.id.image_items);
        heading = itemView.findViewById(R.id.heading_items);
        subheading = itemView.findViewById(R.id.sub_heading_items);
        price = itemView.findViewById(R.id.price_items);
        ratingBar = itemView.findViewById(R.id.rating_items);
        grid_items=itemView.findViewById(R.id.grid_items);
    }
}
public class Adapter_wishlist_fragment extends RecyclerView.Adapter<ViewHolderWish>{
    public ArrayList<Items_list> itemsListStack;
    Context context;

    public static final String imageUrl="imageUrl";
    public static final  String heading_text="heading_text";
    public static final String subheading_text="subheading_text";
    public static final  String price_text="price_text";
    public static final  String rating_text="rating_text";
    public static final  String index="position";

    public Adapter_wishlist_fragment( Context context,ArrayList<Items_list> itemsListStack) {
        this.itemsListStack = itemsListStack;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderWish onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_for_items_fragment,parent,false);
        ViewHolderWish viewHolderWish=new ViewHolderWish(view);
        return viewHolderWish;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWish holder, int position) {
        holder.heading.setText(itemsListStack.get(position).heading);
        holder.subheading.setText(itemsListStack.get(position).subheading);
        holder.price.setText(itemsListStack.get(position).price);
        holder.ratingBar.setRating(itemsListStack.get(position).setRating);
        String url = itemsListStack.get(position).imageUrl;
        Glide.with(context).load(url).into(holder.product);
        holder.grid_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, Items_discription.class);
                intent.putExtra(index,holder.getAdapterPosition());
                intent.putExtra(imageUrl,itemsListStack.get(holder.getAdapterPosition()).imageUrl);
                intent.putExtra(heading_text,itemsListStack.get(holder.getAdapterPosition()).heading);
                intent.putExtra(subheading_text,itemsListStack.get(holder.getAdapterPosition()).subheading);
                intent.putExtra(price_text,itemsListStack.get(holder.getAdapterPosition()).price);
                intent.putExtra(rating_text,itemsListStack.get(holder.getAdapterPosition()).setRating);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemsListStack.size();
    }
}
