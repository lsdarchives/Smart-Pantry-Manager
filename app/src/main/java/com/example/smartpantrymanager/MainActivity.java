package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button pantryBtn = findViewById(R.id.pantryBtn);
        Button recipesBtn = findViewById(R.id.pantryBtn);
        Button settingsBtn = findViewById(R.id.pantryBtn);

        // Button functionality
        pantryBtn.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,PantryActivity.class);
        });
        recipesBtn.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
        });
        settingsBtn.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        });
    }
}