package com.example.medimap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView btnLogin;

    DatabaseHelper registerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration); // your XML layout file

        // Initialize UI elements
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        registerHelper = new DatabaseHelper(this); // SQLite helper

        // Register button logic
        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // VALIDATION
            if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                etName.setError("Enter valid name");
            } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Enter valid email");
            } else if (phone.isEmpty() || !phone.matches("\\d{10}")) {
                etPhone.setError("Enter 10-digit phone number");
            } else if (password.length() < 6) {
                etPassword.setError("Minimum 6 characters");
            } else if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
            } else {
                // Insert data
                boolean inserted = registerHelper.insertUser(name, email, phone, password);
                if (inserted) {
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    etName.setText(""); etEmail.setText(""); etPhone.setText("");
                    etPassword.setText(""); etConfirmPassword.setText("");
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Login redirect (optional: add Intent here)
        btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "login page...", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        });
    }
}
