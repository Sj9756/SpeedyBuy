package com.example.speedybuy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.Adapters.Cart_recycler_adapter;
import com.example.speedybuy.Adapters.Item_list_recy_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.example.speedybuy.category.Mens;
import com.example.speedybuy.database.Database_cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fragment_cart extends Fragment {
    LottieAnimationView lottieAnimationView;
    ArrayList<Items_list>items_array=new ArrayList<>();
    RecyclerView cart_recycler;

    public Fragment_cart(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView = lottieAnimationView;
    }



    public Fragment_cart() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        cart_recycler=view.findViewById(R.id.cart_recycler);
        getData();
        return view;
    }
    private void getData() {
        try (Database_cart databaseCart =new Database_cart(requireContext())){
           items_array= databaseCart.itemsListsArray();
           cart_recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
           Cart_recycler_adapter ad=new Cart_recycler_adapter(items_array,requireContext(),"Fragment_cart");
           cart_recycler.setAdapter(ad);
        }
    }


}