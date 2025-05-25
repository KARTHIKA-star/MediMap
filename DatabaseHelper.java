package com.example.medimap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medimap.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "users";
    private static final String COL_ID = "id";
    public static final String COL_NAME = "name";     // username stored in 'name'
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(query);
    }

    // Handle DB upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert new user
    public boolean insertUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // true if insert successful
    }
    public Cursor getLastRegisteredUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1", null);
    }
    // Get user by username (name)


    // Update user by original username

}
