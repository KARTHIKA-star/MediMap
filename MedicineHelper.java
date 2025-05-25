package com.example.medimap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicineHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "medimap.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "pharmacy_medicines";
    public static final String COL_ID = "id";
    public static final String COL_PHARMACY = "pharmacy_name";
    public static final String COL_MEDICINE = "medicine_name";
    public static final String COL_AVAIL = "availability";
    public static final String COL_PRICE = "price";

    public MedicineHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PHARMACY + " TEXT, " +
                COL_MEDICINE + " TEXT, " +
                COL_AVAIL + " TEXT, " +
                COL_PRICE + " REAL)";
        db.execSQL(query);

        // Sample data insert
        db.execSQL("INSERT INTO " + TABLE_NAME + " (pharmacy_name, medicine_name, availability, price) " +
                "VALUES " +
                "('Apollo', 'Paracetamol', 'Available', 50.0), " +
                "('Apollo', 'Ibuprofen', 'Not Available', 70.0), " +
                "('MedPlus', 'Dolo 650', 'Available', 45.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getMedicine(String pharmacy, String medicine) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT availability, price FROM " + TABLE_NAME +
                " WHERE pharmacy_name = ? AND medicine_name = ?", new String[]{pharmacy, medicine});
    }
}
