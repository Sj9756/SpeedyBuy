package com.example.speedybuy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.fragments.Fragment_cart;
import com.example.speedybuy.fragments.Fragment_home;
import com.example.speedybuy.fragments.Fragment_search;
import com.example.speedybuy.fragments.Fragment_setting;
import com.example.speedybuy.fragments.Fragment_wishlist;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
   public static BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        lottieAnimationView = findViewById(R.id.animationView_home);
        Intent intent = getIntent();
        if(!intent.hasExtra("loadFragment")){
            getFragment(new Fragment_home(lottieAnimationView));   //this default fragment home

        }
        if (intent.hasExtra("loadFragment")) {
            String fragmentToLoad = intent.getStringExtra("loadFragment");
            if (fragmentToLoad != null && fragmentToLoad.equals("cart")) {
                getFragment(new Fragment_cart(lottieAnimationView));
                bottomNavigationView.setSelectedItemId(R.id.navigation_cart);
            }
        }






        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (R.id.navigation_home == id) {
                getFragment(new Fragment_home(lottieAnimationView));
            }
           else if (R.id.navigation_search == id) {
                getFragment(new Fragment_search(lottieAnimationView));
            }
           else if (R.id.navigation_cart == id) {
                getFragment(new Fragment_cart(lottieAnimationView));
            }
            else if (R.id.navigation_wishlist == id) {
                getFragment(new Fragment_wishlist(lottieAnimationView));
            } else if (R.id.navigation_setting == id) {
                getFragment(new Fragment_setting(lottieAnimationView));
            }

            return true;
        });
    }

    private void getFragment( @NonNull androidx.fragment.app.Fragment fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_loader, fragmentClass, null)
                .commit();
    }

    }
