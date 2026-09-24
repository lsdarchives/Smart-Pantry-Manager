package com.example.smartpantrymanager.database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

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

    public long addPantryItem(String name, int quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity",quantity);
        values.put("unit",unit);
        values.put("expiryDate",expiryDate);
        return db.insert("PANTRY", null, values);
    }

    public Cursor getAllPantryItems(){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM PANTRY", null);
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
