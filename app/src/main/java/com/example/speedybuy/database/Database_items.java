package com.example.speedybuy.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.speedybuy.Adapters.Items_list;

import java.util.ArrayList;

public class Database_items extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ItemsDB";
    public String TABLE_NAME = "Item";
    private static final int DATABASE_V = 1;

    //column
    private static final String INDEX = "index_no";
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
        String create = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "(" + INDEX + " INT PRIMARY KEY, " + IMAGE_URL + " VARCHAR(200)," + HEADING + " VARCHAR(200)," + SUBHEADING + " VARCHAR(200)," + PRICE + " VARCHAR(200)," + RATING + " FLOAT" + ");";
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
            Items_list item = new Items_list(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getFloat(5));
            itemsLists.add(item);
        }
        cursor.close();
        return itemsLists;
    }

    public ArrayList<Integer> getPosition() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Integer> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + INDEX + " FROM " + this.TABLE_NAME + " ORDER BY " + INDEX + " ASC", null);
        while (cursor.moveToNext()) {
            data.add(cursor.getInt(0));
        }
        cursor.close();
        return data;
    }
    public void deleteRecord(int index){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME,INDEX +" = "+index,null);
    }
}
