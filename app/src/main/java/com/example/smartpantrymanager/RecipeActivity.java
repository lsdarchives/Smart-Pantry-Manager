package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView recipeName;
    private TextView recipeIngredients;
    private TextView recipeInstructions;
    private TextView backRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeName = findViewById(R.id.recipeName);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeInstructions = findViewById(R.id.recipeInstructions);
        backRecipeBtn = findViewById(R.id.backRecipeBtn);

        dbHelper = new DatabaseHelper(this);

        backRecipeBtn.setOnClickListener(view -> finish());

        int recipeId = getIntent().getIntExtra("recipeId", -1);

        if (recipeId != -1) {
            loadRecipe(recipeId);
        }
    }

    private void loadRecipe(int recipeId) {
        Recipe recipe = dbHelper.getRecipeById(recipeId);

        if (recipe == null) {
            return;
        }

        recipeName.setText(recipe.getName());
        recipeInstructions.setText(recipe.getPreparation());

        List<RecipeIngredient> ingredients = dbHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {
            ingredientText
                    .append("• ")
                    .append(ingredient.getRequiredQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append(" ")
                    .append(ingredient.getIngredientName())
                    .append("\n");
        }

        recipeIngredients.setText(ingredientText.toString());
    }
}