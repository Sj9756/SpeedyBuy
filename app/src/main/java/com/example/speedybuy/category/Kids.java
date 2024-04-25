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

public class Kids extends AppCompatActivity {
    ArrayList<Items_list> item_array = new ArrayList<>();
    RecyclerView recyclerview_kids;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_kids);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerview_kids = findViewById(R.id.recyclerview_kids);
        lottieAnimationView = findViewById(R.id.animationView_kids);
        getData();
        lottieAnimationView.setVisibility(View.VISIBLE);
    }
    private void getData() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference("All_product");

        itemsRef.child("kids").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Items_list itemList = childSnapshot.getValue(Items_list.class);
                    item_array.add(itemList);
                }
                lottieAnimationView.setVisibility(View.INVISIBLE);
                lottieAnimationView.setProgress(0);
                Item_list_recy_adapter ad = new Item_list_recy_adapter(Kids.this, item_array, "Kids");
                recyclerview_kids.setLayoutManager(new GridLayoutManager(Kids.this, 2));
                recyclerview_kids.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}