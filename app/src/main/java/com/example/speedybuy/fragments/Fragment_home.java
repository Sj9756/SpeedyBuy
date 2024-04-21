package com.example.speedybuy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;


import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.speedybuy.Adapters.Home_recycler_view_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.example.speedybuy.category.Accessories;
import com.example.speedybuy.category.Beauty;
import com.example.speedybuy.category.Ethnic;
import com.example.speedybuy.category.Fashion;
import com.example.speedybuy.category.Kids;
import com.example.speedybuy.category.Mens;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_home extends Fragment {

    TextView event_dod_btn, event_time_text_view, event_date_text_view, event_tp_btn;
    AppCompatButton summer_sale_btn;
    ImageView sponsored_btn;
    NestedScrollView scrollview_home;

    RecyclerView recycler_view_suggested, recycler_view_trending_products, recycler_view_home_kids_deal;
    List<SlideModel> slideModelList = new ArrayList<>();
    LinearLayout category_linearlayout;
    ImageSlider imageSlider;
    SearchView searchView;
    CardView cardView;
    LottieAnimationView lottieAnimationView;


    public Fragment_home() {
    }

    public Fragment_home(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView = lottieAnimationView;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        event_time_text_view = view.findViewById(R.id.event_time_text_view);
        event_dod_btn = view.findViewById(R.id.event_dod_btn);
        event_date_text_view = view.findViewById(R.id.event_date_text_view);
        event_tp_btn = view.findViewById(R.id.event_tp_btn);
        summer_sale_btn = view.findViewById(R.id.summer_sale_btn);
        sponsored_btn = view.findViewById(R.id.sponsored_btn);
        scrollview_home = view.findViewById(R.id.scrollview_home);
        searchView = view.findViewById(R.id.search);
        imageSlider = view.findViewById(R.id.image_slider);
        recycler_view_home_kids_deal = view.findViewById(R.id.recycler_view_home_kids_deal);
        recycler_view_suggested = view.findViewById(R.id.recycler_view_suggested);
        recycler_view_trending_products = view.findViewById(R.id.recycler_view_trending_products);
        category_linearlayout = view.findViewById(R.id.category_linearlayout);
        lottieAnimationView.setVisibility(View.VISIBLE);
        category_linearlayout(Beauty.class, view, R.id.home_beauty);
        category_linearlayout(Ethnic.class, view, R.id.home_ethnic);
        category_linearlayout(Fashion.class, view, R.id.home_fashion);
        category_linearlayout(Mens.class, view, R.id.home_mens);
        category_linearlayout(Accessories.class, view, R.id.home_accessories);
        category_linearlayout(Kids.class, view, R.id.home_kid);
        startTimer();
        event_dod_btn.setOnClickListener(v -> {
        });
        event_tp_btn.setOnClickListener(v -> {

        });
        summer_sale_btn.setOnClickListener(v -> {
            Snackbar.make(requireContext(), v, "sale is coming soon", Snackbar.LENGTH_SHORT).setAction("Notify", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        });
        sponsored_btn.setOnClickListener(v -> {

        });
        Slider_images_data();
        getDataFor_recycler_view_suggested();
        getDataFor_recycler_view_trending_products();
        getDataFor_recycler_view_home_kids_deal();
        return view;

    }

    private void category_linearlayout(Class<?> cls, View view, @IdRes int id) {
        LinearLayout linearLayout = view.findViewById(id);
        linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), cls);
            startActivity(intent);
        });
    }

    private void Slider_images_data() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("Slider_images");

        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String itemList = childSnapshot.getValue(String.class);
                    slideModelList.add(new SlideModel(itemList, ScaleTypes.FIT));
                }
                imageSlider.setImageList(slideModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void startTimer() {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            int remainingTime = 24 * 60 * 60;

            @Override
            public void run() {
                int hour = remainingTime / 3600;
                int minutes = (remainingTime % 3600) / 60;
                int seconds = remainingTime % 60;
                String formattedTime = String.format(Locale.US, "%02d" + "h " + "%02d" + "m " + "%02d" + "s remaining", hour, minutes, seconds);
                requireActivity().runOnUiThread(() -> event_time_text_view.setText(formattedTime));

                if (remainingTime <= 0) {
                    timer.cancel();

                }
                remainingTime--;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void getDataFor_recycler_view_suggested() {
        ArrayList<Items_list> item_array = new ArrayList<>();
        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("All_product");

        itemsRef.child("mens").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    item_array.add(itemList);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                lottieAnimationView.setProgress(0);
                scrollview_home.setVisibility(View.VISIBLE);
                Home_recycler_view_adapter ad = new Home_recycler_view_adapter(item_array, requireContext(), "Fragment_home");
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_view_suggested.setLayoutManager(layoutManager);
                recycler_view_suggested.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getDataFor_recycler_view_trending_products() {
        ArrayList<Items_list> item_array = new ArrayList<>();
        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("All_product");

        itemsRef.child("accessories").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    item_array.add(itemList);
                }
                Home_recycler_view_adapter ad = new Home_recycler_view_adapter(item_array, requireContext(), "Fragment_home");
                //  LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                recycler_view_trending_products.setLayoutManager(new GridLayoutManager(requireContext(), 3));
                recycler_view_trending_products.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getDataFor_recycler_view_home_kids_deal() {
        ArrayList<Items_list> item_array = new ArrayList<>();
        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("All_product");

        itemsRef.child("kids").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    item_array.add(itemList);
                }
                Home_recycler_view_adapter ad = new Home_recycler_view_adapter(item_array, requireContext(), "Fragment_home");
                recycler_view_home_kids_deal.setLayoutManager(new GridLayoutManager(requireContext(), 3));
                recycler_view_home_kids_deal.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}