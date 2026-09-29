package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    public static void applySavedTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SmartPantrySettings", Context.MODE_PRIVATE);
        boolean darkMode = preferences.getBoolean("darkMode", false);

        AppCompatDelegate.setDefaultNightMode(darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
