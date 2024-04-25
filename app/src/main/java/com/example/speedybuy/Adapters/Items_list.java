package com.example.speedybuy.Adapters;

public class Items_list {

    public int id;
    public String imageUrl;
    public String heading;
    public String subheading;
    public int price;
    public float setRating;

    public Items_list() {
        // Default constructor required for Firebase deserialization
    }

    public Items_list(int id, String imageUrl, String heading, String subheading, int price, float setRating) {
        this.heading = heading;
        this.subheading = subheading;
        this.price = price;
        this.setRating = setRating;
        this.imageUrl = imageUrl;
        this.id = id;
    }

}
