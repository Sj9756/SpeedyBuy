package com.example.speedybuy.fragmants;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.speedybuy.R;
import com.example.speedybuy.category.Beauty;
import com.example.speedybuy.category.Ethnic;
import com.example.speedybuy.category.Fashion;
import com.example.speedybuy.category.Kids;
import com.example.speedybuy.category.Mens;
import com.example.speedybuy.category.Accessories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_home extends Fragment {
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

        searchView=view.findViewById(R.id.search);
        imageSlider = view.findViewById(R.id.image_slider);

        category_linearlayout=view.findViewById(R.id.category_linearlayout);
        category_linearlayout.setVisibility(View.INVISIBLE);

        imageSlider.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);

        lottieAnimationView.setVisibility(View.VISIBLE);
        category_linearlayout(Beauty.class,view,R.id.home_beauty);
        category_linearlayout(Ethnic.class,view,R.id.home_ethnic);
        category_linearlayout(Fashion.class,view,R.id.home_fashion);
        category_linearlayout(Mens.class,view,R.id.home_mens);
        category_linearlayout(Accessories.class,view,R.id.home_accessories);
        category_linearlayout(Kids.class,view,R.id.home_kid);
        Slider_images_data();
        return view;

    }

    private void category_linearlayout(Class<?> cls, View view , @IdRes int id  ) {
        LinearLayout linearLayout = view.findViewById(id);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), cls);
                startActivity(intent);
            }
        });
    }

    private void Slider_images_data() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("Slider_images");

        itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String itemList = childSnapshot.getValue(String.class);
                    slideModelList.add(new SlideModel(itemList,ScaleTypes.FIT));
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                imageSlider.setVisibility(View.VISIBLE);
                imageSlider.setImageList(slideModelList);
                searchView.setVisibility(View.VISIBLE);
                category_linearlayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}