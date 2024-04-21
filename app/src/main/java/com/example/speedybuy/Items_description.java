package com.example.speedybuy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.speedybuy.Adapters.Item_list_recy_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.database.Database_items;
import com.example.speedybuy.fragments.Fragment_wishlist;
import com.example.speedybuy.key.Ikey;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Items_description extends AppCompatActivity {
    LinearLayout size_layout;
    private TextView selectedTextView,selected_size;
    String fragment_name;
    int position;
    String imageUrl;
    String heading_text;
    String subheading_text;
    float rating_text;
    int id , item_price;
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

        size_layout =findViewById(R.id.size_layout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent intent = getIntent();
        fragment_name = intent.getStringExtra(Ikey.FRAGMENT);
        position = intent.getIntExtra(Ikey.POSITION, -1);
        imageUrl = intent.getStringExtra(Ikey.IMG);
        heading_text = intent.getStringExtra(Ikey.HEADING);
        subheading_text = intent.getStringExtra(Ikey.SUBHEADING);
        item_price = intent.getIntExtra(Ikey.PRICE,0);
        rating_text = intent.getFloatExtra(Ikey.RATING, 0);
        id = intent.getIntExtra(Ikey.ID, -1);
        Glide.with(this).load(imageUrl).into(product);
        String Stprice="₹"+item_price;
        heading.setText(heading_text);
        subheading.setText(subheading_text);
        price.setText(Stprice);
        ratingBar.setRating(rating_text);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        iconSetter = getWishlistIconColor();
        MenuItem menuItem = menu.findItem(R.id.wish_toolbar);
        if (iconSetter) {
            menuItem.setIcon(R.drawable.ic_heart_pressed);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try (Database_items items = new Database_items(Items_description.this)
        ) {
            int rid = item.getItemId();
            if (rid == R.id.wish_toolbar) {
                if (iconSetter) {
                    item.setIcon(R.drawable.ic_heart_unpressed);
                    items.deleteRecord(id);
                    if (fragment_name.equals("fragment_wishlist")) {
                        RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                        Item_list_recy_adapter ad = (Item_list_recy_adapter) recyclerView.getAdapter();
                        if (ad != null) {
                            ad.items.remove(position);
                            ad.notifyItemRemoved(position);
                        }
                    }
                    Snackbar.make(findViewById(android.R.id.content), "Product is removed from wishlist", Snackbar.LENGTH_SHORT).show();
                    iconSetter = false;
                } else {
                    item.setIcon(R.drawable.ic_heart_pressed);
                    items.insertRecord(id,imageUrl,heading_text,subheading_text,item_price,rating_text);
                    if (fragment_name.equals("fragment_wishlist")) {
                        RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                        Item_list_recy_adapter ad = (Item_list_recy_adapter) recyclerView.getAdapter();
                        if (ad != null) {
                            ad.items.add(position, new Items_list(id, imageUrl, heading_text, subheading_text, item_price, rating_text));
                            ad.notifyItemChanged(position);
                        }
                    }
                    Snackbar.make(findViewById(android.R.id.content), "Product is added to wishlist", Snackbar.LENGTH_SHORT)
                            .setAction("Undo", v -> {
                                item.setIcon(R.drawable.ic_heart_unpressed);
                                items.deleteRecord(id);
                                iconSetter = false;
                                if (fragment_name.equals("fragment_wishlist")) {
                                    RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
                                    Item_list_recy_adapter ad = (Item_list_recy_adapter) recyclerView.getAdapter();
                                    if (ad != null) {
                                        ad.items.remove(position);
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

    public boolean getWishlistIconColor() {
        try (Database_items databaseItems = new Database_items(this)) {
            ArrayList<Integer> data_baseIndex = databaseItems.getId();
            return data_baseIndex.contains(id);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void handleSelection(TextView textView) {
        if (selectedTextView != null) {
            selectedTextView.setSelected(false);
            selectedTextView.setTextColor(getColor(R.color.colorPrimary));

        }
        textView.setTextColor(getColor(R.color.white));
        textView.setSelected(true);
        selectedTextView = textView;
        selected_size=findViewById(R.id.selected_size);
        String size="Size: "+selectedTextView.getText();
        selected_size.setText(size);
    }

    public void onOptionSelected(View view) {
        handleSelection((TextView) view);
    }
}
