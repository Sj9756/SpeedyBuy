package com.example.speedybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.database.Database_items;
import com.example.speedybuy.fragmants.Fragment_home;
import com.example.speedybuy.fragmants.Fragment_search;
import com.example.speedybuy.fragmants.Fragment_setting;
import com.example.speedybuy.fragmants.Fragment_wishlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        getFragment(Fragment_home.class);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (R.id.navigation_home == id) {
                    getFragment(Fragment_home.class);
                } else if (R.id.navigation_wishlist == id) {
                    getFragment(Fragment_wishlist.class);
                } else if (R.id.navigation_setting == id) {
                    getFragment(Fragment_setting.class);
                }
                return true;
            }
        });
    }

    private void getFragment(@NonNull Class<? extends androidx.fragment.app.Fragment> fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_loader, fragmentClass, null)
                .commit();
    }

}