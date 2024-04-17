package com.example.speedybuy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database_Op {
    private static final String INDEX="index_no";
    private static final String IMAGE_URL="image_url";
    private static final String HEADING="heading";
    private static final String SUBHEADING="subheading";
    private static final String PRICE="price";
    private static final String RATING="rating";
    private static final String TABLE_NAME="Item";
    private SQLiteDatabase databaseMain;
    Database_items database;

    public Database_Op(Context context) {
        database = new Database_items(context);
    }

    public void open() throws SQLException {
        databaseMain=database.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void insertRecord(int index,String imageUrl,String heading,String subheading,int price,float rating) {
        ContentValues values = new ContentValues();
        values.put(INDEX,index);
        values.put(IMAGE_URL, imageUrl);
        values.put(HEADING, heading);
        values.put(SUBHEADING, subheading);
        values.put(PRICE, price);
        values.put(RATING, rating);
        databaseMain.insert(TABLE_NAME,null,values);
    }

}
