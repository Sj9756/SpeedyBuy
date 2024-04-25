package com.example.speedybuy.category;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.speedybuy.Adapters.Item_list_recy_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ethnic extends AppCompatActivity {
    ArrayList<Items_list> item_array = new ArrayList<>();
    RecyclerView recyclerview_ethnic;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_ethnic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerview_ethnic = findViewById(R.id.recyclerview_ethnic);
        lottieAnimationView = findViewById(R.id.animationView_ethnic);
        getData();
        lottieAnimationView.setVisibility(View.VISIBLE);
    }
    private void getData() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("All_product");

        itemsRef.child("ethnic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    item_array.add(itemList);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                lottieAnimationView.setProgress(0);
                Item_list_recy_adapter ad = new Item_list_recy_adapter(Ethnic.this, item_array, "Ethnic");
                recyclerview_ethnic.setLayoutManager(new GridLayoutManager(Ethnic.this, 2));
                recyclerview_ethnic.setAdapter(ad);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}