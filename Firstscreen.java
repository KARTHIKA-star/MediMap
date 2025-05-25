package com.example.medimap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class Firstscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Firstscreen.this, HomePageActivity.class); // Change to your main activity
            startActivity(intent);
            finish();
        }, 2000); // 2 seconds delay
    }
}
