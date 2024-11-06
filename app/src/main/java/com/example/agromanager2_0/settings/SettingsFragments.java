package com.example.agromanager2_0.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.example.agromanager2_0.R;

public class SettingsFragments extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
