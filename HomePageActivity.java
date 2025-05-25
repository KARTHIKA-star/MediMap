package com.example.medimap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    TextView tvGreeting, tvAdvice;
    Button btnGoToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tvGreeting = findViewById(R.id.tvGreeting);
        tvAdvice = findViewById(R.id.tvAdvice);
        btnGoToSearch = findViewById(R.id.btnGoToSearch);

        // Get username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "User");

        // Set greeting message
        String greeting = "Welcome, " + username + "! 🌟";
        tvGreeting.setText(greeting);

        // Set advice text
        String advice = "💊 Always consult your doctor before taking any new medicine.\n" +
                "🕒 Take your medicine on time and follow dosage instructions.\n" +
                "🧪 Keep medicines out of reach of children and store them in a cool, dry place.";
        tvAdvice.setText(advice);

        // Set button action
        btnGoToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
