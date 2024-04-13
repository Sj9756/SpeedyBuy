package com.example.speedybuy.fragmants;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.speedybuy.Adapters.Adapter_wishlist_fragment;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.example.speedybuy.database.Database_Op;
import com.example.speedybuy.database.Database_items;

import java.util.ArrayList;
import java.util.Stack;


public class Fragment_wishlist extends Fragment {
    RecyclerView recyclerView_wishlist;

    public Fragment_wishlist() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView_wishlist = view.findViewById(R.id.recy_wishlist);
        Database_items databaseItems=new Database_items(requireContext());
        ArrayList<Items_list>itemsLists=databaseItems.itemsListsArray();
        Adapter_wishlist_fragment ad = new Adapter_wishlist_fragment(requireContext(),itemsLists);
        recyclerView_wishlist.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView_wishlist.setAdapter(ad);
        return view;
    }


}