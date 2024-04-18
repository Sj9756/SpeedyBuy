package com.example.speedybuy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.speedybuy.Adapters.Items_list;

import java.util.ArrayList;

public class Database_items extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ItemsDB";
    public String TABLE_NAME = "Item";
    private static final int DATABASE_V = 1;

    //column
    private static final String ID = "id";
    private static final String IMAGE_URL = "image_url";
    private static final String HEADING = "heading";
    private static final String SUBHEADING = "subheading";
    private static final String PRICE = "price";
    private static final String RATING = "rating";

    public Database_items(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_V);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "(" + ID + " INT PRIMARY KEY, " + IMAGE_URL + " VARCHAR(200)," + HEADING + " VARCHAR(200)," + SUBHEADING + " VARCHAR(200)," + PRICE + " INT," + RATING + " FLOAT" + ");";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Items_list> itemsListsArray() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Items_list> itemsLists = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + this.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Items_list item = new Items_list(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(5));
            itemsLists.add(item);
        }
        cursor.close();
        return itemsLists;
    }

    public ArrayList<Integer> getId() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Integer> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + ID + " FROM " + this.TABLE_NAME + " ORDER BY " + ID + " ASC", null);
        while (cursor.moveToNext()) {
            data.add(cursor.getInt(0));
        }
        cursor.close();
        return data;
    }
    public void deleteRecord(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,ID +" = "+id,null);
        database.close();
    }
    public void insertRecord(int id,String imageUrl,String heading,String subheading,int price,float rating) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,id);
        values.put(IMAGE_URL, imageUrl);
        values.put(HEADING, heading);
        values.put(SUBHEADING, subheading);
        values.put(PRICE, price);
        values.put(RATING, rating);
        database.insert(TABLE_NAME,null,values);
    }
}
