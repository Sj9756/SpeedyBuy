package com.example.speedybuy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Items_description;
import com.example.speedybuy.R;
import com.example.speedybuy.key.Ikey;

import java.util.ArrayList;

class Home_View_Holder extends RecyclerView.ViewHolder {
    LinearLayout suggested_linear_layout;
    ImageView product;
    TextView heading, subheading, price;
    public Home_View_Holder(@NonNull View itemView) {
        super(itemView);
        product = itemView.findViewById(R.id.image_items_home);
        heading = itemView.findViewById(R.id.heading_items_home);
        subheading = itemView.findViewById(R.id.sub_heading_items_home);
        price = itemView.findViewById(R.id.price_items_home);
        suggested_linear_layout=itemView.findViewById(R.id.suggested_linear_layout);
    }
}
public class Home_adapter extends RecyclerView.Adapter<Home_View_Holder> {
ArrayList<Items_list>itemsLists;
Context context;
    String fragment_name;

    public Home_adapter(ArrayList<Items_list> itemsLists,Context context,String fragment_name) {
        this.itemsLists = itemsLists;
        this.context=context;
        this.fragment_name=fragment_name;
    }

    @NonNull
    @Override
    public Home_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_home_recyler,parent,false);
        Home_View_Holder holder=new Home_View_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Home_View_Holder holder, int position) {
        Items_list currentItem = itemsLists.get(position);
        holder.heading.setText(currentItem.heading);
        holder.subheading.setText(currentItem.subheading);
        String price="₹"+currentItem.price;
        holder.price.setText(price);
        String url = currentItem.imageUrl;
        Glide.with(context).load(url).into(holder.product);
        holder.suggested_linear_layout.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return itemsLists.size();
    }
}
