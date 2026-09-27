package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;

import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantry);

        // Find the RecyclerView from activity_pantry.xml
        recyclerView = findViewById(R.id.recyclerPantry);

        // Give the RecyclerView a vertical layout
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Connect to our SQLite database
        dbHelper = new DatabaseHelper(this);

        // Get all pantry items from the database
        List<PantryItem> pantryItems =
                dbHelper.getAllPantryItems();

        // Give the data to our adapter
        adapter = new PantryAdapter(pantryItems);

        // Connect the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        // Add Ingredient button
        Button addIngredientBtn =
                findViewById(R.id.addIngredientBtn);

        addIngredientBtn.setOnClickListener(view -> {

            Intent intent = new Intent(
                    PantryActivity.this,
                    AddEditActivity.class
            );

            startActivity(intent);
        });
    }
}