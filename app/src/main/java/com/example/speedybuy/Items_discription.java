package com.example.speedybuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.fragmants.Fragment_wishlist;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class Items_discription extends AppCompatActivity {
    String imageUrl;
    String heading_text;
    String subheading_text;
    String price_text;
    float rating_text;
    MaterialToolbar toolbar;
    ImageView product;
    TextView heading, subheading, price;
    RatingBar ratingBar;
    boolean check_wishlist=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items_discription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar_item_dis);
        product=findViewById(R.id.dis_image_items);
        heading=findViewById(R.id.dis_heading_items);
        subheading=findViewById(R.id.dis_sub_heading_items);
        price=findViewById(R.id.dis_price_items);
        ratingBar=findViewById(R.id.dis_rating_items);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        Intent intent = getIntent();
         imageUrl = intent.getStringExtra("imageUrl");
         heading_text = intent.getStringExtra("heading_text");
         subheading_text = intent.getStringExtra("subheading_text");
         price_text = intent.getStringExtra("price_text");
         rating_text= intent.getFloatExtra("rating_text",0);

        Glide.with(this).load(imageUrl).into(product);
        heading.setText(heading_text);
        subheading.setText(subheading_text);
        price.setText(price_text);
        ratingBar.setRating(rating_text);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.wish_toolbar){
            if(!check_wishlist){
                item.setIcon(R.drawable.heart_pressed);
                Snackbar.make(findViewById(android.R.id.content), "Product is added to wishlist", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", v -> {
                            item.setIcon(R.drawable.heart_unpressed);
                            check_wishlist = false;
                            Items_list itemsList=new Items_list(heading_text,subheading_text,price_text,rating_text,imageUrl);
                            Fragment_wishlist ff=new Fragment_wishlist();
                            ff.itemsLists.add(itemsList);
                        })
                        .show();

                check_wishlist=true;
            }
            else {
                item.setIcon(R.drawable.heart_unpressed);
                Snackbar.make(findViewById(android.R.id.content), "Product is removed from wishlist", Snackbar.LENGTH_SHORT).show();
                check_wishlist=false;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}