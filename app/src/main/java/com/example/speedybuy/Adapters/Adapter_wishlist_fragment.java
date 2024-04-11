package com.example.speedybuy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speedybuy.R;

import java.util.ArrayList;

class ViewHolderWish extends RecyclerView.ViewHolder {
    public ViewHolderWish(@NonNull View itemView) {
        super(itemView);
    }
}
public class Adapter_wishlist_fragment extends RecyclerView.Adapter<ViewHolderWish>{
    ArrayList<Items_list>itemsLists;
    Context context;

    public Adapter_wishlist_fragment(ArrayList<Items_list> itemsLists, Context context) {
        this.itemsLists = itemsLists;
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

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
