package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartpantrymanager.adapters.RecipeAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerRecipes;
    private TextView emptyRecipesText;
    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        emptyRecipesText = findViewById(R.id.emptyRecipesText);

        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<Recipe> recipes = dbHelper.getAllRecipes();

        List<Recipe> matchingRecipes =
                RecipeMatcher.findMatchingRecipes(
                        recipes,
                        pantryItems,
                        dbHelper
                );

        if (matchingRecipes.isEmpty()) {

            recyclerRecipes.setVisibility(RecyclerView.GONE);
            emptyRecipesText.setVisibility(TextView.VISIBLE);

        } else {

            recyclerRecipes.setVisibility(RecyclerView.VISIBLE);
            emptyRecipesText.setVisibility(TextView.GONE);

            adapter = new RecipeAdapter(
                    this,
                    matchingRecipes,
                    dbHelper
            );

            recyclerRecipes.setAdapter(adapter);
        }
    }
}