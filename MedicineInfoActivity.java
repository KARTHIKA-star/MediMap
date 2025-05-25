package com.example.medimap;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Locale;

public class MedicineInfoActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btnSpeakAll;
    private SwitchCompat switchLanguage;
    private TextView tvDosage, tvPositives, tvNegatives;
    private TextView labelDosage, labelPositives, labelNegatives;
    private boolean isEnglish = true;

    private TextToSpeech textToSpeech; // TTS object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);

        etSearch = findViewById(R.id.etSearch);
        btnSpeakAll = findViewById(R.id.btnSpeakAll);
        switchLanguage = findViewById(R.id.switchLanguage);

        labelDosage = findViewById(R.id.labelDosage);
        labelPositives = findViewById(R.id.labelPositives);
        labelNegatives = findViewById(R.id.labelNegatives);

        tvDosage = findViewById(R.id.tvDosage);
        tvPositives = findViewById(R.id.tvPositives);
        tvNegatives = findViewById(R.id.tvNegatives);

        // Initialize TTS
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = Locale.ENGLISH;
                textToSpeech.setLanguage(locale);
            }
        });

        // Language switch listener
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isEnglish = isChecked;
            searchMedicine(etSearch.getText().toString());
        });

        // Search button
        ImageButton btnVoiceSearch = findViewById(R.id.btnVoiceSearch);
        btnVoiceSearch.setOnClickListener(v -> {
            String medicineName = etSearch.getText().toString().trim();
            if (!medicineName.isEmpty()) {
                searchMedicine(medicineName);
            } else {
                Toast.makeText(MedicineInfoActivity.this, "Please enter a medicine name", Toast.LENGTH_SHORT).show();
            }
        });

        // Speak All Button
        btnSpeakAll.setOnClickListener(v -> {
            speakAllDetails();
        });
    }

    private void searchMedicine(String medicineName) {
        // Reset views
        labelDosage.setVisibility(View.GONE);
        tvDosage.setVisibility(View.GONE);
        labelPositives.setVisibility(View.GONE);
        tvPositives.setVisibility(View.GONE);
        labelNegatives.setVisibility(View.GONE);
        tvNegatives.setVisibility(View.GONE);

        String dosage = "", positives = "", negatives = "";

        if (medicineName.equalsIgnoreCase("Aspirin")) {
            if (isEnglish) {
                dosage = "Take 1 tablet twice daily after meals.";
                positives = "Helps in reducing fever, body ache, and inflammation.";
                negatives = "May cause stomach irritation or bleeding.";
            } else {
                dosage = "ஒரு டேபிளெட் காலை மற்றும் மாலை உணவுக்கு பிறகு எடுத்துக் கொள்ளுங்கள்.";
                positives = "பித்தம், உடல் வலி மற்றும் அழற்சி குறைக்க உதவுகிறது.";
                negatives = "வயிற்று எரிச்சல் அல்லது இரத்தம் வெளியேறுவதைக் காரணமாகக் கூடும்.";
            }
        } else if (medicineName.equalsIgnoreCase("Paracetamol")) {
            if (isEnglish) {
                dosage = "Take 1 tablet every 4-6 hours, not exceeding 4 tablets a day.";
                positives = "Helps in reducing fever and relieving pain.";
                negatives = "May cause liver damage if taken in excess.";
            } else {
                dosage = "ஒரு டேபிளெட் 4-6 மணி நேரத்திற்கு எடுத்துக் கொள்ளுங்கள், நாளைக்கு 4 டேபிளெட்டுகளுக்கு அதிகம் அல்ல.";
                positives = "பித்தம் மற்றும் வலி நிவர்த்தி செய்ய உதவுகிறது.";
                negatives = "அதிகமாக எடுத்தால் கல்லீரல் சேதம் ஏற்படலாம்.";
            }
        }

        if (!dosage.isEmpty() || !positives.isEmpty() || !negatives.isEmpty()) {
            displayMedicineInfo(dosage, positives, negatives);
        } else {
            Toast.makeText(this, "No information found for " + medicineName, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMedicineInfo(String dosage, String positives, String negatives) {
        labelDosage.setVisibility(View.VISIBLE);
        tvDosage.setVisibility(View.VISIBLE);
        tvDosage.setText(dosage);

        labelPositives.setVisibility(View.VISIBLE);
        tvPositives.setVisibility(View.VISIBLE);
        tvPositives.setText(positives);

        labelNegatives.setVisibility(View.VISIBLE);
        tvNegatives.setVisibility(View.VISIBLE);
        tvNegatives.setText(negatives);
    }

    private void speakAllDetails() {
        String dosage = tvDosage.getText().toString();
        String positives = tvPositives.getText().toString();
        String negatives = tvNegatives.getText().toString();

        if (dosage.isEmpty() && positives.isEmpty() && negatives.isEmpty()) {
            Toast.makeText(this, "No content to speak. Search for a medicine first.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder fullText = new StringBuilder();

        if (!dosage.isEmpty()) {
            fullText.append("Dosage: ").append(dosage).append(". ");
        }
        if (!positives.isEmpty()) {
            fullText.append("Advantages: ").append(positives).append(". ");
        }
        if (!negatives.isEmpty()) {
            fullText.append("Disadvantages: ").append(negatives).append(".");
        }

        // Set language
        Locale locale = isEnglish ? Locale.ENGLISH : new Locale("ta", "IN");
        int result = textToSpeech.setLanguage(locale);

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(this, "Language not supported on this device", Toast.LENGTH_SHORT).show();
        } else {
            textToSpeech.speak(fullText.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
