package com.example.medimap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private CardView medicineListCard;
    private CardView pharmacySearchCard;
    private CardView viewProfileCard;
    private CardView medicineSearchCard;
    private CardView logoutCard;
    private CardView homeCard;

    private String username; // To store the logged-in username

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Get username from intent
        username = getIntent().getStringExtra("username");

        // Initialize CardViews
        homeCard = findViewById(R.id.homeCard);
        medicineListCard = findViewById(R.id.medicineListCard);
        pharmacySearchCard = findViewById(R.id.pharmacySearchCard);
        viewProfileCard = findViewById(R.id.viewProfileCard);
        logoutCard = findViewById(R.id.logoutCard);

        // Home (Maps)
        homeCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Medicine List
        medicineListCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MedicineItemActivity.class);
            startActivity(intent);
        });

        // Pharmacy Info
        pharmacySearchCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MedicineInfoActivity.class);
            startActivity(intent);
        });

        // ✅ View Profile – passing only the username
        viewProfileCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewProfileActivity.class);
            intent.putExtra("username", username); // Pass the username
            startActivity(intent);
        });

        // Logout
        logoutCard.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, Firstscreen.class);
            startActivity(intent);
            finish(); // Optional: close dashboard on logout
        });
    }
}
