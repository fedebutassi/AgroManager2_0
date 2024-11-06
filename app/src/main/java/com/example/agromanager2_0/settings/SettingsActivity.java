package com.example.agromanager2_0.settings;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cargar el fragmento de preferencias
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragments())
                .commit();
    }
}
