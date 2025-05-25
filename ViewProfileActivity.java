package com.example.medimap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {

    TextInputEditText tvName, tvEmail, tvPhone;
    TextView tvPassword;
    ImageView imgProfile, ivTogglePassword;
    Button btnLogout;
    DatabaseHelper dbHelper;

    boolean isPasswordVisible = false;
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // Initialize views
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvPassword = findViewById(R.id.tvPassword);
        imgProfile = findViewById(R.id.imgProfile);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        btnLogout = findViewById(R.id.btnLogout);

        dbHelper = new DatabaseHelper(this);

        // Load the latest registered user details
        Cursor cursor = dbHelper.getLastRegisteredUser();
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            // Set values
            tvName.setText(name);
            tvEmail.setText(email);
            tvPhone.setText(phone);
            tvPassword.setText(maskPassword(password));

            cursor.close();
        } else {
            Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
        }

        // Toggle password visibility
        ivTogglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                tvPassword.setText(maskPassword(password));
                ivTogglePassword.setImageResource(R.drawable.invisible);
                isPasswordVisible = false;
            } else {
                tvPassword.setText(password);
                ivTogglePassword.setImageResource(R.drawable.visible);
                isPasswordVisible = true;
            }
        });

        // Logout action
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(ViewProfileActivity.this, MainActivity.class));
            finish();
        });
    }

    // Utility to mask password with asterisks
    private String maskPassword(String password) {
        if (password == null || password.isEmpty()) return "";
        return "*".repeat(password.length());
    }
}
