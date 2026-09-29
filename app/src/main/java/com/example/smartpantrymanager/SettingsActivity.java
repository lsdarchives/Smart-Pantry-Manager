package com.example.smartpantrymanager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;

public class SettingsActivity extends AppCompatActivity {
    private SwitchCompat darkModeSwitch;
    private SwitchCompat expiryAlertSwitch;
    private SwitchCompat showExpiredSwitch;
    private TextView expiryPeriodText;
    private Button expiryPeriodBtn;
    private Spinner defaultUnitSpinner;
    private TextView backSettingsBtn;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ThemeManager.applySavedTheme(this);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("SmartPantrySettings", MODE_PRIVATE);


        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        expiryAlertSwitch = findViewById(R.id.expiryAlertSwitch);
        showExpiredSwitch = findViewById(R.id.showExpiredSwitch);
        expiryPeriodText = findViewById(R.id.expiryPeriodText);
        expiryPeriodBtn = findViewById(R.id.expiryPeriodBtn);
        defaultUnitSpinner = findViewById(R.id.defaultUnitSpinner);
        backSettingsBtn = findViewById(R.id.backSettingsBtn);

        setupDefaultUnitSpinner();
        loadSettings();
        setupListeners();

        backSettingsBtn.setOnClickListener(view -> finish());

        findViewById(R.id.navHome).setOnClickListener(view -> {
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            finish();
        });

        findViewById(R.id.navPantry).setOnClickListener(view -> {
            startActivity(new Intent(SettingsActivity.this, PantryActivity.class));
            finish();
        });

        findViewById(R.id.navRecipes).setOnClickListener(view -> {
            startActivity(new Intent(SettingsActivity.this, SuggestedRecipesActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSettings();
    }

    private void setupDefaultUnitSpinner() {

        String[] units = {
                "g",
                "kg",
                "ml",
                "L",
                "pieces",
                "slices"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        defaultUnitSpinner.setAdapter(adapter);
    }

    private void loadSettings() {
        boolean darkMode = preferences.getBoolean("darkMode", false);

        boolean expiryAlerts = preferences.getBoolean("expiryAlerts", true);
        boolean showExpired = preferences.getBoolean("showExpired", true);
        int expiryDays = preferences.getInt("expiryDays", 7);
        String defaultUnit = preferences.getString("defaultUnit", "g");

        darkModeSwitch.setChecked(darkMode);
        expiryAlertSwitch.setChecked(expiryAlerts);
        showExpiredSwitch.setChecked(showExpired);
        expiryPeriodText.setText("Warning period: " + expiryDays + " days");

        setSpinnerSelection(defaultUnit);
    }

    private void setupListeners() {
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked)
                -> {preferences.edit().putBoolean("darkMode", isChecked).apply();

            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        expiryAlertSwitch.setOnCheckedChangeListener((buttonView, isChecked)
                -> preferences.edit().putBoolean("expiryAlerts", isChecked).apply());

        showExpiredSwitch.setOnCheckedChangeListener((buttonView, isChecked)
                -> preferences.edit().putBoolean("showExpired", isChecked).apply());

        expiryPeriodBtn.setOnClickListener(view -> showExpiryPeriodDialog());


        defaultUnitSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    android.widget.AdapterView<?> parent,
                    android.view.View view,
                    int position,
                    long id) {

                String selectedUnit = parent.getItemAtPosition(position).toString();

                preferences.edit().putString("defaultUnit", selectedUnit).apply();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerSelection(String unit) {
        for (int i = 0; i < defaultUnitSpinner.getCount(); i++) {
            if (defaultUnitSpinner.getItemAtPosition(i).toString().equals(unit)) {
                defaultUnitSpinner.setSelection(i);
                break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showExpiryPeriodDialog() {
        String[] periods = {
                "1 day",
                "3 days",
                "7 days",
                "14 days",
                "30 days"
        };

        new androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Expiry Warning Period").setItems(periods, (dialog, which) -> {
            int[] days = {
                    1,
                    3,
                    7,
                    14,
                    30
            };

            int selectedDays = days[which];

            preferences.edit().putInt("expiryDays", selectedDays).apply();
            expiryPeriodText.setText("Warning period: " + selectedDays + " days");
        })
                .show();

    }
}