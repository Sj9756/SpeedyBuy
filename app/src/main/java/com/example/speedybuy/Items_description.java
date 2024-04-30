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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speedybuy.Adapters.Item_list_recy_adapter;
import com.example.speedybuy.Adapters.Items_list;
import com.example.speedybuy.database.Database_cart;
import com.example.speedybuy.database.Database_wishlist;
import com.example.speedybuy.fragments.Fragment_wishlist;
import com.example.speedybuy.key.Ikey;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Items_description extends AppCompatActivity {

    BottomNavigationView bottomNavigationItemView;
    LinearLayout size_layout;
    private TextView selectedTextView;

    String fragment_name;
    int position;
    String imageUrl;
    String heading_text;
    String subheading_text;
    float rating_text;
    int id, item_price;


    private boolean iconSetter = false;
    private boolean textSetterCart = false;
    MaterialToolbar toolbar;
    ImageView product_image_1, product_image_2, product_image_3;
    TextView heading_view, subheading_view, price_view;
    AppCompatButton add_cart_btn, buy_btn;
    RatingBar ratingBar_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_discription);
        initializeViews();
        addActionBar();
        set_content();
        setAdd_cart_btn();


        add_cart_btn.setOnClickListener(v -> insertData());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        setWishlistIcon(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int rid = item.getItemId();
        if (rid == R.id.wish_toolbar) {
            WishlistAction(item);
        } else if (rid == R.id.cart_toolbar) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("loadFragment", "cart");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void handleSelection(TextView textView) {
        if (selectedTextView != null) {
            selectedTextView.setSelected(false);
            selectedTextView.setTextColor(getColor(R.color.colorPrimary));

        }
        textView.setTextColor(getColor(R.color.white));
        textView.setSelected(true);
        selectedTextView = textView;
        TextView selected_size = findViewById(R.id.selected_size);  //this is conform size text view Size=?
        String size = "Size: " + selectedTextView.getText();
        selected_size.setText(size);
    }

    public void onOptionSelected(View view) {
        handleSelection((TextView) view);
    }

    private void set_content() {
        Intent intent = getIntent();
        fragment_name = intent.getStringExtra(Ikey.FRAGMENT);
        position = intent.getIntExtra(Ikey.POSITION, -1);
        imageUrl = intent.getStringExtra(Ikey.IMG);
        heading_text = intent.getStringExtra(Ikey.HEADING);
        subheading_text = intent.getStringExtra(Ikey.SUBHEADING);
        item_price = intent.getIntExtra(Ikey.PRICE, 0);
        rating_text = intent.getFloatExtra(Ikey.RATING, 0);
        id = intent.getIntExtra(Ikey.ID, -1);
        Glide.with(this).load(imageUrl).into(product_image_1);
        Glide.with(this).load(imageUrl).into(product_image_2);
        Glide.with(this).load(imageUrl).into(product_image_3);
        String Stprice = "₹" + item_price;
        heading_view.setText(heading_text);
        subheading_view.setText(subheading_text);
        price_view.setText(Stprice);
        ratingBar_view.setRating(rating_text);
    }

    private void initializeViews() {
        bottomNavigationItemView = MainActivity.bottomNavigationView;
        toolbar = findViewById(R.id.toolbar_item_dis);
        product_image_1 = findViewById(R.id.dis_item_image1);
        product_image_2 = findViewById(R.id.dis_item_image2);
        product_image_3 = findViewById(R.id.dis_item_image3);
        heading_view = findViewById(R.id.dis_heading_items);
        subheading_view = findViewById(R.id.dis_sub_heading_items);
        price_view = findViewById(R.id.dis_price_items);
        ratingBar_view = findViewById(R.id.dis_rating_items);
        add_cart_btn = findViewById(R.id.add_cart_btn);
        buy_btn = findViewById(R.id.buy_btn);
        size_layout = findViewById(R.id.size_layout);

    }


    private void insertData() {
        if (textSetterCart) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("loadFragment", "cart");
            startActivity(intent);
        } else {
            try (Database_cart cartItem = new Database_cart(Items_description.this)) {
                cartItem.insertRecord(id, imageUrl, heading_text, subheading_text, item_price, rating_text);
                add_cart_btn.setText(getString(R.string.go_to_cart));
                textSetterCart = true;
            } catch (Exception e) {
                Log.e("cartItem", "Error occurred", e);
            }


        }
    }

    private void addActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void setWishlistIcon(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.wish_toolbar);
        try (Database_wishlist databaseItems = new Database_wishlist(this)) {
            iconSetter = databaseItems.dataExist(id);
            if (iconSetter) {
                menuItem.setIcon(R.drawable.ic_heart_pressed);
            }
        }
    }

    private void WishlistAction(@NonNull MenuItem item) {
        try (Database_wishlist items = new Database_wishlist(Items_description.this)) {
            if (iconSetter) {
                item.setIcon(R.drawable.ic_heart_unpressed);
                items.deleteRecord(id);
                WishlistRecyclerViewNotify();
                iconSetter = false;
                Snackbar.make(findViewById(android.R.id.content), "Product is removed from wishlist", Snackbar.LENGTH_SHORT).show();
            } else {
                item.setIcon(R.drawable.ic_heart_pressed);
                items.insertRecord(id, imageUrl, heading_text, subheading_text, item_price, rating_text);
                WishlistRecyclerViewNotify();
                iconSetter = true;
                Snackbar.make(findViewById(android.R.id.content), "Product is added to wishlist", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", v -> {
                            item.setIcon(R.drawable.ic_heart_unpressed);
                            items.deleteRecord(id);
                            WishlistRecyclerViewNotify();
                            iconSetter = false;
                        }).show();
            }
        } catch (Exception e) {
            Log.e("WishlistAction", "Error occurred", e);
        }
    }

    private void WishlistRecyclerViewNotify() {
        if (fragment_name.equals("fragment_wishlist")) {
            RecyclerView recyclerView = Fragment_wishlist.recyclerView_wishlist;
            Item_list_recy_adapter ad = (Item_list_recy_adapter) recyclerView.getAdapter();
            if (ad != null && iconSetter) {
                ad.items.remove(position);
                ad.notifyItemRemoved(position);

            } else if (ad != null) {
                ad.items.add(position, new Items_list(id, imageUrl, heading_text, subheading_text, item_price, rating_text));
                ad.notifyItemChanged(position);
                iconSetter = true;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setAdd_cart_btn() {
        try (Database_cart databaseCart = new Database_cart(Items_description.this)) {
            textSetterCart = databaseCart.dataExist(id);
            if (textSetterCart) {
                add_cart_btn.setText(getString(R.string.go_to_cart));
            } else {
                add_cart_btn.setText(getString(R.string.add_to_cart));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdd_cart_btn();
    }
}
