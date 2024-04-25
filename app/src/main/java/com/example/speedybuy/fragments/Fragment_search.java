package com.example.speedybuy.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.R;


public class Fragment_search extends Fragment {
    LottieAnimationView lottieAnimationView;

    public Fragment_search() {
    }

    public Fragment_search(LottieAnimationView lottieAnimationView) {
        this.lottieAnimationView=lottieAnimationView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }
}