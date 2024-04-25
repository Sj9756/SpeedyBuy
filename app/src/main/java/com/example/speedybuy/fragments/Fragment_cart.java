package com.example.speedybuy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView  cart_item_qty_text_view,cart_item_qty_price_text_view,cart_discount_text_view,cart_extra_off_text_view, cart_total_text_view;
    LottieAnimationView lottieAnimationView;
    ArrayList<Items_list> items_array = new ArrayList<>();
    RecyclerView cart_recycler;
    LinearLayout price_chart_layout;

    public Fragment_cart(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView = lottieAnimationView;
    }


    public Fragment_cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initializeViewsPriceLayout(view);
        getData();
        return view;
    }

    private void getData() {
        try (Database_cart databaseCart = new Database_cart(requireContext())) {
            items_array = databaseCart.itemsListsArray();
            cart_recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
            Cart_recycler_adapter ad = new Cart_recycler_adapter(items_array, requireContext(), "Fragment_cart",cart_item_qty_text_view,cart_item_qty_price_text_view,cart_total_text_view,price_chart_layout);
            cart_recycler.setAdapter(ad);
        }

    }
private void initializeViewsPriceLayout(View view){
    cart_recycler = view.findViewById(R.id.cart_recycler);
    price_chart_layout = view.findViewById(R.id.price_chart_layout);
    cart_item_qty_text_view=view.findViewById(R.id.cart_item_qty_text_view);
    cart_item_qty_price_text_view=view.findViewById(R.id.cart_item_qty_price_text_view);
    cart_discount_text_view=view.findViewById(R.id.cart_discount_text_view);
    cart_extra_off_text_view=view.findViewById(R.id.cart_extra_off_text_view);
    cart_total_text_view=view.findViewById(R.id.cart_total_text_view);
}

}