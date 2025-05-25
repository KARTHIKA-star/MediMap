package com.example.medimap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    DatabaseHelper registerHelper;

    public static final String PREFS_NAME = "UserPrefs";
    public static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Login screen layout

        // Initialize UI
        etUsername = findViewById(R.id.username_edittext);
        etPassword = findViewById(R.id.password_edittext);
        btnLogin = findViewById(R.id.login_button);
        TextView signupText = findViewById(R.id.signup_text);

        // Initialize SQLite helper
        registerHelper = new DatabaseHelper(this);

        // Move to Registration screen
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        // Login button click
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                String fetchedName = validateUser(username, password);
                if (fetchedName != null) {
                    // Save username in SharedPreferences
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_USERNAME, fetchedName); // Save the real name or username
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to home/dashboard
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish(); // prevent returning back to login screen
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check username and password in DB
    private String validateUser(String username, String password) {
        SQLiteDatabase db = registerHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME +
                " WHERE " + DatabaseHelper.COL_NAME + " = ? AND " + DatabaseHelper.COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        String name = null;
        if (cursor.moveToFirst()) {
            // You can also fetch other user info here
            name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)); // Or change to full name if available
        }

        cursor.close();
        db.close();
        return name;
    }
}
