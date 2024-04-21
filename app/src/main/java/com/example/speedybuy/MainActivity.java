package com.example.speedybuy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.fragments.Fragment_home;
import com.example.speedybuy.fragments.Fragment_setting;
import com.example.speedybuy.fragments.Fragment_wishlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        lottieAnimationView = findViewById(R.id.animationView_home);
       getFragment(new Fragment_home(lottieAnimationView));
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (R.id.navigation_home == id) {
                getFragment(new Fragment_home(lottieAnimationView));
            } else if (R.id.navigation_wishlist == id) {
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
