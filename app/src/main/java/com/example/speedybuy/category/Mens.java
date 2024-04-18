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

public class Mens extends AppCompatActivity {

    ArrayList<Items_list> item_array = new ArrayList<>();
    RecyclerView recyclerview_mens;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_mens);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerview_mens = findViewById(R.id.recyclerview_mens);
        lottieAnimationView = findViewById(R.id.animationView_mens);
      getData();
        lottieAnimationView.setVisibility(View.VISIBLE);

    }

    private void getData() {

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
                Item_list_recy_adapter ad = new Item_list_recy_adapter(Mens.this, item_array, "Mens");
                recyclerview_mens.setLayoutManager(new GridLayoutManager(Mens.this, 2));
                recyclerview_mens.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void insertdata() {
//        ArrayList<Items_list>ethnic=new ArrayList<>();
//        Items_list itemsList1=new Items_list(41,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-apparel-combo/y/x/q/5-6-years-sis-l-s-ch-micky-1p-show-in-style-original-imagyexxv7nzqane.jpeg?q=70&crop=false","Show In Style",
//                                "Boys Casual Vest Shorts  (Multicolor)",399,3f);
//
//        Items_list itemsList2=new Items_list(42,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-apparel-combo/u/p/b/6-7-years-1052-prpl-ultinity-original-imagw8vshufggq6a.jpeg?q=70&crop=false","ULTINITY",
//                "Girls Party(Festive) Top Pyjama  (Multicolor)",333,3.8f);
//
//        Items_list itemsList3=new Items_list(43,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-t-shirt/e/q/l/3-4-years-kuctshrt232-kuchipoo-original-imagx36ey2hk9qc7.jpeg?q=70&crop=false","Kuchipoo",
//                        "Boys Printed Cotton Blend T Shirt  (Multicolor, Pack of 5)",500,4.2f);
//
//        Items_list itemsList4=new Items_list(44,"https://rukminim2.flixcart.com/image/832/832/xif0q/sweatshirt/t/h/k/14-15-years-nuucoswt0572-5fk-nusyl-original-imagvyyvts7kkmhg.jpeg?q=70&crop=false","Nusyl",
//                "Girls Full Sleeve Printed Sweatshirt",400,4.2f);
//
//        Items_list itemsList5=new Items_list(45,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-short/c/z/v/13-14-years-ji-t2fshort-13-14-fasla-original-imag3d8pzucwza7t-bb.jpeg?q=70&crop=false","Fasla",
//                                "Short For Boys Casual Printed, Solid Pure Cotton  (Multicolor, Pack of 5)#JustHere",199,5f);
//
//
//        Items_list itemsList6=new Items_list(46,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-apparel-combo/j/i/4/6-7-years-printed-emblica-original-imagpyf2hzfp6kms.jpeg?q=70&crop=false","EMBLICA",
//                "Baby Boys Casual T-shirt Pant  (Multicolor)",237,4f);
//
//        Items_list itemsList7=new Items_list(47,"https://rukminim2.flixcart.com/image/832/832/xif0q/shirt/v/h/m/11-12-years-624-j-woven-stack-original-imagxgtyj9y9gfak.jpeg?q=70&crop=false","GADHIYA TRENDZ",
//                                "Boys Slim Fit Printed Spread Collar Casual Shir",560,4f);
//
//        Items_list itemsList8=new Items_list(48,"https://rukminim2.flixcart.com/image/832/832/xif0q/kids-t-shirt/7/e/t/8-9-years-px1rcbvirat-kids-rjm-original-imagzhqaw3bhtwac.jpeg?q=70&crop=false","UNIQ",
//                                "Boys & Girls Printed Polyester T Shirt  (Multicolor, Pack of 1)",299,4.2F);
//
//
//
//        ethnic.add(itemsList1);
//        ethnic.add(itemsList2);
//        ethnic.add(itemsList3);
//        ethnic.add(itemsList4);
//        ethnic.add(itemsList5);
//        ethnic.add(itemsList6);
//        ethnic.add(itemsList7);
//        ethnic.add(itemsList8);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("All_product");
//        myRef.child("kids").setValue(ethnic);
//
//
//    }
}