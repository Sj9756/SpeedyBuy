package com.example.speedybuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Adapters.Adapter_wishlist_fragment;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.database.Database_Op;
import com.example.speedybuy.database.Database_items;
import com.example.speedybuy.fragmants.Fragment_wishlist;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Stack;

public class Items_discription extends AppCompatActivity {
    String context;
    int mainposition;
    String imageUrl;
    String heading_text;
    String subheading_text;
    String price_text;
    float rating_text;
    int position;

    boolean iconSetter = false;

    MaterialToolbar toolbar;
    ImageView product;
    TextView heading, subheading, price;
    RatingBar ratingBar;

    Stack<Items_list> stack_of_item = new Stack<>();

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
        product = findViewById(R.id.dis_image_items);
        heading = findViewById(R.id.dis_heading_items);
        subheading = findViewById(R.id.dis_sub_heading_items);
        price = findViewById(R.id.dis_price_items);
        ratingBar = findViewById(R.id.dis_rating_items);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        Intent intent = getIntent();
        context = intent.getStringExtra("context");
        mainposition = intent.getIntExtra("mainposition", -1);
        imageUrl = intent.getStringExtra("imageUrl");
        heading_text = intent.getStringExtra("heading_text");
        subheading_text = intent.getStringExtra("subheading_text");
        price_text = intent.getStringExtra("price_text");
        rating_text = intent.getFloatExtra("rating_text", 0);
        position = intent.getIntExtra("position", -1);
        Glide.with(this).load(imageUrl).into(product);
        heading.setText(heading_text);
        subheading.setText(subheading_text);
        price.setText(price_text);
        ratingBar.setRating(rating_text);


//        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//
//            }
//        };
//
//        getOnBackPressedDispatcher().
//
//                addCallback(this, onBackPressedCallback);
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        iconSetter = getWishlistIcon();
        MenuItem menuItem = menu.findItem(R.id.wish_toolbar);
        if (iconSetter) {
            menuItem.setIcon(R.drawable.heart_pressed);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Database_items items = new Database_items(Items_discription.this);
        int id = item.getItemId();

        if (id == R.id.wish_toolbar) {
            if (iconSetter) {
                item.setIcon(R.drawable.heart_unpressed);
                items.deleteRecord(position);
                if (context.startsWith("ViewHolderWish")) {
                    RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                    Adapter_wishlist_fragment  ad = (Adapter_wishlist_fragment) recyclerView.getAdapter();
                    ad.itemsListStack.remove(mainposition);
                    ad.notifyItemRemoved(mainposition);
                }
                iconSetter = false;
            } else {
                item.setIcon(R.drawable.heart_pressed);
                Database_Op op = new Database_Op(this);
                op.open();
                op.insertRecord(position, imageUrl, heading_text, subheading_text, price_text, rating_text);
                Snackbar.make(findViewById(android.R.id.content), "Product is added to wishlist", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                item.setIcon(R.drawable.heart_unpressed);
                                items.deleteRecord(position);
                                iconSetter = false;

                            }
                        }).show();
                iconSetter = true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean getWishlistIcon() {
        Database_items databaseItems = new Database_items(this);
        ArrayList<Integer> data_baseIndex = databaseItems.getPosition();
        return data_baseIndex.contains(position);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
