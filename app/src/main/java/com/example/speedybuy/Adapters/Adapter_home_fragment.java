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
import com.example.speedybuy.Items_discription;
import com.example.speedybuy.R;

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
    public static final String imageUrl="imageUrl";
    public static final  String heading_text="heading_text";
    public static final String subheading_text="subheading_text";
    public static final  String price_text="price_text";
    public static final  String rating_text="rating_text";
    public static final  String index="position";
    ArrayList<Items_list> itemems;

    Context context;
    private ArrayList<Items_list> filteredItemList;

    public Adapter_home_fragment(Context context, ArrayList<Items_list> itemems) {
        this.itemems = itemems;
        this.filteredItemList = new ArrayList<>(itemems);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_items_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items_list currentItem = filteredItemList.get(position);
        holder.heading.setText(currentItem.heading);
        holder.subheading.setText(currentItem.subheading);
        holder.price.setText(currentItem.price);
        holder.ratingBar.setRating(currentItem.setRating);
        String url = currentItem.imageUrl;
        Glide.with(context).load(url).into(holder.product);
        holder.grid_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,Items_discription.class);
                intent.putExtra(index,holder.getAdapterPosition());
                intent.putExtra(imageUrl,currentItem.imageUrl);
                intent.putExtra(heading_text,currentItem.heading);
                intent.putExtra(subheading_text,currentItem.subheading);
                intent.putExtra(price_text,currentItem.price);
                intent.putExtra(rating_text,currentItem.setRating);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredItemList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                FilterResults results = new FilterResults();
                ArrayList<Items_list> filteredList = new ArrayList<>();

                if (filterPattern.isEmpty()) {
                    filteredList.addAll(itemems);
                } else {

                    for (Items_list item : itemems) {
                        if (item.heading.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItemList.clear();
                filteredItemList.addAll((ArrayList<Items_list>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
