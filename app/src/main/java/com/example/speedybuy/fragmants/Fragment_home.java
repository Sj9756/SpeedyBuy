package com.example.speedybuy.fragmants;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.speedybuy.Adapters.Adapter_home_fragment;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_home extends Fragment {
    ImageSlider imageSlider;
    ArrayList<Items_list> itemem_array=new ArrayList<>();
    SearchView searchView;
    CardView cardView;
    RecyclerView recyclerview_home_fragmet;
    LottieAnimationView lottieAnimationView;

    public Fragment_home() {
    }

    public Fragment_home(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView=lottieAnimationView;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerview_home_fragmet=view.findViewById(R.id.recyclerview_home_fragment);
        getData();
        lottieAnimationView.setVisibility(View.VISIBLE);
        searchView=view.findViewById(R.id.search);
        cardView=view.findViewById(R.id.search_card);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        imageSlider= view.findViewById(R.id.image_slider);
        List<SlideModel> slideModelList=new ArrayList<>();
        String url1="https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/1b1ee0ac046bc394.jpg?q=20";
        String url2="https://rukminim2.flixcart.com/fk-p-flap/480/80/image/c8f548688def283c.jpg?q=20";
        String url3="https://rukminim2.flixcart.com/fk-p-flap/480/80/image/bd94c9e6358f3a70.jpg?q=20";
        String url4="https://rukminim2.flixcart.com/fk-p-flap/480/210/image/946492be05a86aad.png?q=20";
        slideModelList.add(new SlideModel(url1, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(url1, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(url1, ScaleTypes.FIT));
        slideModelList.add(new SlideModel(url4, ScaleTypes.FIT));
        imageSlider.setImageList(slideModelList);
        return view;

    }


    private boolean getData() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("items");
        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    itemem_array.add(itemList);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                lottieAnimationView.setProgress(0);
                Adapter_home_fragment ad=new Adapter_home_fragment(requireContext(),itemem_array);
                recyclerview_home_fragmet.setLayoutManager(new GridLayoutManager(requireContext(),2));
                recyclerview_home_fragmet.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    return true;
    }

}