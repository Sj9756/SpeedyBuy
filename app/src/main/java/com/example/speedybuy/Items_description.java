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

import androidx.activity.EdgeToEdge;
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
import com.example.speedybuy.key.Ikey;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Items_description extends AppCompatActivity {
    String context;
    int position;
    String imageUrl;
    String heading_text;
    String subheading_text;
    String price_text;
    float rating_text;
    int id;
    boolean iconSetter = false;
    MaterialToolbar toolbar;
    ImageView product;
    TextView heading, subheading, price;
    RatingBar ratingBar;

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
        context = intent.getStringExtra(Ikey.FRAGMENT);
        position = intent.getIntExtra(Ikey.POSITION, -1);
        imageUrl = intent.getStringExtra(Ikey.IMG);
        heading_text = intent.getStringExtra(Ikey.HEADING);
        subheading_text = intent.getStringExtra(Ikey.SUBHEADING);
        price_text = intent.getStringExtra(Ikey.PRICE);
        rating_text = intent.getFloatExtra(Ikey.RATING, 0);
        id = intent.getIntExtra(Ikey.ID, -1);
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
        iconSetter = getWishlistIcon();
        MenuItem menuItem = menu.findItem(R.id.wish_toolbar);
        if (iconSetter) {
            menuItem.setIcon(R.drawable.heart_pressed);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try (Database_items items = new Database_items(Items_description.this)
        ) {
            int rid = item.getItemId();
            if(rid==R.id.cart_toolbar){
                Database_Op ss=new Database_Op(this);
                ss.open();
            }
            if (rid == R.id.wish_toolbar) {
                if (iconSetter) {
                    item.setIcon(R.drawable.heart_unpressed);
                    items.deleteRecord(id);
                    if (context.startsWith("ViewHolderWish")) {
                        RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                        Adapter_wishlist_fragment ad = (Adapter_wishlist_fragment) recyclerView.getAdapter();
                        if (ad != null) {
                            ad.itemsListStack.remove(position);
                            ad.notifyItemRemoved(position);
                        }

                    }
                    Snackbar.make(findViewById(android.R.id.content), "Product is removed from wishlist", Snackbar.LENGTH_SHORT).show();
                    iconSetter = false;
                } else {
                    item.setIcon(R.drawable.heart_pressed);
                    Database_Op op = new Database_Op(this);
                    op.open();
                    op.insertRecord(id, imageUrl, heading_text, subheading_text, price_text, rating_text);
                    if (context.startsWith("ViewHolderWish")) {
                        RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                        Adapter_wishlist_fragment ad = (Adapter_wishlist_fragment) recyclerView.getAdapter();
                        if (ad != null) {
                            ad.itemsListStack.add(position, new Items_list(id, imageUrl, heading_text, subheading_text, price_text, rating_text));
                            ad.notifyItemChanged(position);
                        }
                    }
                    Snackbar.make(findViewById(android.R.id.content), "Product is added to wishlist", Snackbar.LENGTH_SHORT)
                            .setAction("Undo", v -> {
                                item.setIcon(R.drawable.heart_unpressed);
                                items.deleteRecord(position);
                                iconSetter = false;
                                if (context.startsWith("ViewHolderWish")) {
                                    RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                                    Adapter_wishlist_fragment ad = (Adapter_wishlist_fragment) recyclerView.getAdapter();
                                    if (ad != null) {
                                        ad.itemsListStack.remove(position);
                                        ad.notifyItemRemoved(position);
                                    }
                                }
                            }).show();
                    iconSetter = true;
                }
            }
        } catch (Exception e) {
            Log.e("Items_description", "Error occurred", e);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean getWishlistIcon() {
        try (Database_items databaseItems = new Database_items(this)) {
            ArrayList<Integer> data_baseIndex = databaseItems.getPosition();
            return data_baseIndex.contains(id);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
