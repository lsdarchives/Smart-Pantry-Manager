package com.example.smartpantrymanager.database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.smartpantrymanager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VER = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE PANTRY (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiryDate TEXT" +
                ")";

        db.execSQL(createPantryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PANTRY");
        onCreate(db);
    }

    public long addPantryItem(String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity",quantity);
        values.put("unit",unit);
        values.put("expiryDate",expiryDate);
        return db.insert("PANTRY", null, values);
    }

    public List<PantryItem> getAllPantryItems(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<PantryItem> pantryItems = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY", null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));

                PantryItem item = new PantryItem(id,name,quantity,unit,expiryDate);

                pantryItems.add(item);
            } while(cursor.moveToNext());
        }
        cursor.close();

        return pantryItems;
    }

    public PantryItem getPantryItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY WHERE id = ?", new String[]{String.valueOf(id)});

        PantryItem item = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));

            item = new PantryItem(id, name, quantity, unit, expiryDate);
        }
        cursor.close();
        return item;
    }
    public int updatePantryItem(int id, String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiryDate", expiryDate);

        return db.update("PANTRY",values,"id = ?", new String[]{String.valueOf(id)});
    }

    public int deletePantryItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("PANTRY", "id = ?", new String[]{String.valueOf(id)});
    }



}
