package com.example.speedybuy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.Adapters.Item_list_recy_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.example.speedybuy.database.Database_wishlist;

import java.util.ArrayList;

public class Fragment_wishlist extends Fragment {
    ArrayList<Items_list> itemsLists=new ArrayList<>();
    LottieAnimationView lottieAnimationView;

    public static RecyclerView recyclerView_wishlist;

    public Fragment_wishlist() {
    }

    public Fragment_wishlist(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView = lottieAnimationView;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView_wishlist = view.findViewById(R.id.recyclerview_wishlist);
        getData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
     getData();
    }
    private void getData(){
        try(Database_wishlist databaseItems = new Database_wishlist(requireContext())){
            itemsLists = databaseItems.itemsListsArray();
            Item_list_recy_adapter ad = new Item_list_recy_adapter(requireContext(),itemsLists,"fragment_wishlist");
            if(itemsLists.isEmpty()){
                recyclerView_wishlist.setLayoutManager(new GridLayoutManager(requireContext(), 1));
            }
            else {
                recyclerView_wishlist.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            }
            recyclerView_wishlist.setAdapter(ad);
        }catch (Exception e){
            Log.d("errorDatabae",e.toString());
        }
    }
}