package com.example.medimap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SyrupsActivity extends AppCompatActivity {

    // Declare views for medicine cards
    private ImageView items_images1, items_images2, items_images3, items_images4, items_images5;
    private TextView items_names1, items_names2, items_names3, items_names4, items_names5;
    private TextView items_quantity1, items_quantity2, items_quantity3, items_quantity4, items_quantity5;
    private TextView items_prices1, items_prices2, items_prices3, items_prices4, items_prices5;

    // Footer buttons
    private Button btnSyrups, btnOintment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syrups); // Replace with your actual XML file name

        // Initialize medicine card views (example for first two)
        items_images1 = findViewById(R.id.items_images1);
        items_names1 = findViewById(R.id.items_names1);
        items_quantity1 = findViewById(R.id.items_quantity1);
        items_prices1 = findViewById(R.id.items_prices1);

        items_images2 = findViewById(R.id.items_images2);
        items_names2 = findViewById(R.id.items_names2);
        items_quantity2 = findViewById(R.id.items_quantity2);
        items_prices2 = findViewById(R.id.items_prices2);

        // ... Initialize items_images3, items_names3, etc. as needed

        // Initialize footer buttons
        btnSyrups = findViewById(R.id.btnSyrups);


        // Set click listeners for footer buttons
        btnSyrups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Show a message or open a new activity
                Toast.makeText(SyrupsActivity.this, "Medicine clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SyrupsActivity.this, MedicineItemActivity.class);
                startActivity(intent);
            }
        });



        // (Optional) Set click listeners for medicine cards if you want
        items_images1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SyrupsActivity.this, items_names1.getText() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Repeat for other medicine cards as needed
    }
}
