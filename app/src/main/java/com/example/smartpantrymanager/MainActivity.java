package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button pantryBtn = findViewById(R.id.pantryBtn);
        Button recipesBtn = findViewById(R.id.recipesBtn);
        TextView settingsIcon = findViewById(R.id.settingsIcon);

        // Button functionality
        pantryBtn.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,PantryActivity.class);
            startActivity(intent);
        });
        recipesBtn.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });
        settingsIcon.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}