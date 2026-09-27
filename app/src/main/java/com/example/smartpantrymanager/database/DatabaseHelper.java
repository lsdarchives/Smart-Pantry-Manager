package com.example.smartpantrymanager.database;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VER = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE PANTRY (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiryDate TEXT" +
                ")";

        db.execSQL(createPantryTable);

        String createRecipesTable = "CREATE TABLE RECIPES (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "preparation TEXT NOT NULL" +
                ")";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable = "CREATE TABLE RECIPE_INGREDIENTS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipeId INTEGER NOT NULL, " +
                "ingredientName TEXT NOT NULL, " +
                "requiredQuantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "FOREIGN KEY(recipeId) REFERENCES RECIPES(id)" +
                ")";

        db.execSQL(createRecipeIngredientsTable);

        seedDefaultRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {

            db.execSQL("CREATE TABLE RECIPES (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL, " + "preparation TEXT NOT NULL" + ")");
            db.execSQL("CREATE TABLE RECIPE_INGREDIENTS (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "recipeId INTEGER NOT NULL, " + "ingredientName TEXT NOT NULL, " + "requiredQuantity REAL NOT NULL, " + "unit TEXT NOT NULL, " + "FOREIGN KEY(recipeId) REFERENCES RECIPES(id)" + ")");

            seedDefaultRecipes(db);
        }
    }

    private void updateRecipePreparations(SQLiteDatabase db) {

        updatePreparation(db, "Chicken Pasta",
                "1. Cook the pasta according to the package instructions.\n\n" +
                        "2. Cook the chicken and onion in a pan.\n\n" +
                        "3. Add the tomatoes and cook until softened.\n\n" +
                        "4. Combine everything with the pasta and serve.");

        updatePreparation(db, "Vegetable Stir Fry",
                "1. Heat the oil in a pan.\n\n" +
                        "2. Add the vegetables and onion.\n\n" +
                        "3. Add the soy sauce.\n\n" +
                        "4. Stir-fry until the vegetables are tender.");

        updatePreparation(db, "Tomato Omelette",
                "1. Beat the eggs in a bowl.\n\n" +
                        "2. Add the chopped tomato and onion.\n\n" +
                        "3. Heat oil in a pan.\n\n" +
                        "4. Pour in the egg mixture and cook until set.");

        updatePreparation(db, "Grilled Cheese Sandwich",
                "1. Butter the bread.\n\n" +
                        "2. Add the cheese between the slices.\n\n" +
                        "3. Heat a pan or grill.\n\n" +
                        "4. Grill until the bread is golden and the cheese melts.");

        updatePreparation(db, "Chicken Wrap",
                "1. Cook the chicken thoroughly.\n\n" +
                        "2. Prepare the tortilla.\n\n" +
                        "3. Add the chicken, lettuce, tomato and cheese.\n\n" +
                        "4. Fold the tortilla and serve.");

        updatePreparation(db, "Pancakes",
                "1. Mix the flour, eggs, milk and sugar into a batter.\n\n" +
                        "2. Heat a pan and add butter.\n\n" +
                        "3. Pour a portion of batter into the pan.\n\n" +
                        "4. Cook both sides until golden.");

        updatePreparation(db, "French Toast",
                "1. Whisk the eggs, milk and cinnamon together.\n\n" +
                        "2. Dip each slice of bread into the mixture.\n\n" +
                        "3. Heat butter in a pan.\n\n" +
                        "4. Fry the bread until golden on both sides.");

        updatePreparation(db, "Fried Rice",
                "1. Cook the rice.\n\n" +
                        "2. Cook the eggs in a pan.\n\n" +
                        "3. Add the peas and carrot.\n\n" +
                        "4. Add the rice and soy sauce.\n\n" +
                        "5. Stir-fry everything together.");

        updatePreparation(db, "Spaghetti Bolognese",
                "1. Cook the spaghetti according to the package instructions.\n\n" +
                        "2. Brown the mince with onion and garlic.\n\n" +
                        "3. Add the tomato and simmer.\n\n" +
                        "4. Serve the sauce with the spaghetti.");

        updatePreparation(db, "Tuna Pasta",
                "1. Cook the pasta according to the package instructions.\n\n" +
                        "2. Drain the tuna.\n\n" +
                        "3. Mix the tuna with mayonnaise, onion and sweetcorn.\n\n" +
                        "4. Combine the mixture with the pasta and serve.");

        updatePreparation(db, "Chicken Curry",
                "1. Heat the oil in a pan.\n\n" +
                        "2. Cook the onion and chicken.\n\n" +
                        "3. Add the tomato and curry powder.\n\n" +
                        "4. Simmer until the chicken is cooked.\n\n" +
                        "5. Serve with rice.");

        updatePreparation(db, "Beef Burger",
                "1. Shape the beef mince into patties.\n\n" +
                        "2. Cook the patties thoroughly.\n\n" +
                        "3. Prepare the burger buns.\n\n" +
                        "4. Add lettuce, tomato, onion and cheese.\n\n" +
                        "5. Place the beef patties into the buns and serve.");

        updatePreparation(db, "Potato and Egg Hash",
                "1. Dice the potatoes and onion.\n\n" +
                        "2. Heat oil in a pan.\n\n" +
                        "3. Cook the potatoes and onion until golden.\n\n" +
                        "4. Add the eggs.\n\n" +
                        "5. Cook until the eggs are set.");

        updatePreparation(db, "Vegetable Soup",
                "1. Chop the potato, carrot and onion.\n\n" +
                        "2. Add the vegetables, stock and mixed vegetables to a pot.\n\n" +
                        "3. Bring the soup to a boil.\n\n" +
                        "4. Simmer until the vegetables are tender.\n\n" +
                        "5. Serve warm.");

        updatePreparation(db, "Banana Smoothie",
                "1. Peel the bananas.\n\n" +
                        "2. Add the bananas, milk and honey to a blender.\n\n" +
                        "3. Blend until smooth.\n\n" +
                        "4. Serve immediately.");

        updatePreparation(db, "Chicken and Rice",
                "1. Cook the rice.\n\n" +
                        "2. Cook the chicken with onion and carrot.\n\n" +
                        "3. Add a little oil while cooking.\n\n" +
                        "4. Cook until the chicken is thoroughly cooked.\n\n" +
                        "5. Serve the chicken with the rice.");

        updatePreparation(db, "Pasta Primavera",
                "1. Cook the pasta according to the package instructions.\n\n" +
                        "2. Cook the mixed vegetables.\n\n" +
                        "3. Add the cream and parmesan.\n\n" +
                        "4. Combine the vegetables and sauce with the pasta.\n\n" +
                        "5. Serve warm.");

        updatePreparation(db, "Bean Quesadilla",
                "1. Prepare the tortillas.\n\n" +
                        "2. Add the beans, cheese, tomato and onion.\n\n" +
                        "3. Fold the tortillas.\n\n" +
                        "4. Cook until the tortillas are crisp and the cheese melts.\n\n" +
                        "5. Serve warm.");
    }

    private void updatePreparation(SQLiteDatabase db, String recipeName, String preparation) {
        ContentValues values = new ContentValues();
        values.put("preparation", preparation);

        db.update("RECIPES", values, "name = ?", new String[]{recipeName});
    }

    private void seedDefaultRecipes(SQLiteDatabase db) {

        if (getRecipeCount(db) > 0) {
            return;
        }

        addRecipeWithIngredients(
                db,
                "Chicken Pasta",
                "Cook the pasta. Cook the chicken and onion in a pan, add tomatoes, then combine with the pasta.",
                new String[]{"chicken", "pasta", "tomato", "onion"},
                new double[]{200, 250, 2, 1},
                new String[]{"g", "g", "pieces", "piece"});

        addRecipeWithIngredients(
                db,
                "Vegetable Stir Fry",
                "Heat oil, cook the vegetables and onion, then add soy sauce and stir-fry until tender.",
                new String[]{"mixed vegetables", "onion", "soy sauce", "oil"},
                new double[]{300, 1, 30, 15},
                new String[]{"g", "piece", "ml", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Tomato Omelette",
                "Beat the eggs, add chopped tomato and onion, then cook in a lightly oiled pan.",
                new String[]{"eggs", "tomato", "onion", "oil"},
                new double[]{3, 1, 0.5, 10},
                new String[]{"pieces", "piece", "piece", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Grilled Cheese Sandwich",
                "Butter the bread, add cheese and grill until the cheese melts.",
                new String[]{"bread", "cheese", "butter"},
                new double[]{2, 2, 10},
                new String[]{"slices", "slices", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Chicken Wrap",
                "Cook the chicken, then fill tortillas with chicken, lettuce, tomato and cheese.",
                new String[]{"chicken", "tortilla", "lettuce", "tomato", "cheese"},
                new double[]{150, 2, 50, 1, 30},
                new String[]{"g", "pieces", "g", "piece", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Pancakes",
                "Mix the flour, eggs, milk and sugar into a batter. Cook portions in a buttered pan.",
                new String[]{"flour", "eggs", "milk", "sugar", "butter"},
                new double[]{200, 2, 250, 30, 20},
                new String[]{"g", "pieces", "ml", "g", "g"}
        );

        addRecipeWithIngredients(
                db,
                "French Toast",
                "Whisk the eggs, milk and cinnamon together. Dip the bread in the mixture and fry in butter.",
                new String[]{"bread", "eggs", "milk", "cinnamon", "butter"},
                new double[]{2, 2, 100, 2, 10},
                new String[]{"slices", "pieces", "ml", "g", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Fried Rice",
                "Cook the rice, then stir-fry it with eggs, peas, carrot, soy sauce and oil.",
                new String[]{"rice", "eggs", "peas", "carrot", "soy sauce", "oil"},
                new double[]{250, 2, 100, 1, 20, 15},
                new String[]{"g", "pieces", "g", "piece", "ml", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Spaghetti Bolognese",
                "Cook the spaghetti. Brown the mince with onion and garlic, add tomato and simmer before serving.",
                new String[]{"spaghetti", "mince", "tomato", "onion", "garlic", "oil"},
                new double[]{250, 250, 2, 1, 2, 15},
                new String[]{"g", "g", "pieces", "piece", "cloves", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Tuna Pasta",
                "Cook the pasta and mix with tuna, mayonnaise, onion and sweetcorn.",
                new String[]{"pasta", "tuna", "mayonnaise", "onion", "sweetcorn"},
                new double[]{250, 1, 30, 0.5, 80},
                new String[]{"g", "can", "g", "piece", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Chicken Curry",
                "Cook the onion and chicken, add tomato and curry powder, then simmer and serve with rice.",
                new String[]{"chicken", "onion", "tomato", "curry powder", "oil", "rice"},
                new double[]{250, 1, 2, 15, 15, 250},
                new String[]{"g", "piece", "pieces", "g", "ml", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Beef Burger",
                "Cook the beef patties and assemble them in buns with lettuce, tomato, onion and cheese.",
                new String[]{"beef mince", "burger buns", "lettuce", "tomato", "onion", "cheese"},
                new double[]{200, 2, 30, 1, 0.5, 2},
                new String[]{"g", "pieces", "g", "piece", "piece", "slices"}
        );

        addRecipeWithIngredients(
                db,
                "Potato and Egg Hash",
                "Cook diced potatoes and onion until golden, then add eggs and cook until set.",
                new String[]{"potato", "eggs", "onion", "oil"},
                new double[]{300, 2, 1, 15},
                new String[]{"g", "pieces", "piece", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Vegetable Soup",
                "Cook the vegetables and stock together until tender, then serve warm.",
                new String[]{"potato", "carrot", "onion", "stock", "mixed vegetables"},
                new double[]{150, 2, 1, 500, 200},
                new String[]{"g", "pieces", "piece", "ml", "g"}
        );

        addRecipeWithIngredients(
                db,
                "Banana Smoothie",
                "Blend the banana, milk and honey until smooth.",
                new String[]{"banana", "milk", "honey"},
                new double[]{2, 300, 15},
                new String[]{"pieces", "ml", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Chicken and Rice",
                "Cook the chicken with onion and carrot, then serve with cooked rice.",
                new String[]{"chicken", "rice", "carrot", "onion", "oil"},
                new double[]{200, 250, 1, 1, 15},
                new String[]{"g", "g", "piece", "piece", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Pasta Primavera",
                "Cook the pasta and vegetables, then combine with cream and parmesan.",
                new String[]{"pasta", "mixed vegetables", "cream", "parmesan", "oil"},
                new double[]{250, 250, 100, 30, 15},
                new String[]{"g", "g", "ml", "g", "ml"}
        );

        addRecipeWithIngredients(
                db,
                "Bean Quesadilla",
                "Fill tortillas with beans, cheese, tomato and onion, then cook until crisp.",
                new String[]{"tortilla", "beans", "cheese", "tomato", "onion"},
                new double[]{2, 150, 60, 1, 0.5},
                new String[]{"pieces", "g", "g", "piece", "piece"}
        );
    }

    private int getRecipeCount(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RECIPES", null);

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    private void addRecipeWithIngredients(
            SQLiteDatabase db,
            String name,
            String preparation,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues = new ContentValues();

        recipeValues.put("name", name);
        recipeValues.put("preparation", preparation);

        long recipeId = db.insert("RECIPES", null, recipeValues);

        if (recipeId == -1) {
            return;
        }

        for (int i = 0; i < ingredientNames.length; i++) {

            ContentValues ingredientValues = new ContentValues();

            ingredientValues.put("recipeId", recipeId);
            ingredientValues.put("ingredientName", ingredientNames[i]);
            ingredientValues.put("requiredQuantity", quantities[i]);
            ingredientValues.put("unit", units[i]);

            db.insert("RECIPE_INGREDIENTS", null, ingredientValues);
        }
    }
    public long addPantryItem(String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity",quantity);
        values.put("unit",unit);
        values.put("expiryDate",expiryDate);
        return db.insert("PANTRY", null, values);
    }

    public List<PantryItem> getAllPantryItems(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<PantryItem> pantryItems = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY", null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));

                PantryItem item = new PantryItem(id,name,quantity,unit,expiryDate);

                pantryItems.add(item);
            } while(cursor.moveToNext());
        }
        cursor.close();

        return pantryItems;
    }

    public PantryItem getPantryItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY WHERE id = ?", new String[]{String.valueOf(id)});

        PantryItem item = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));

            item = new PantryItem(id, name, quantity, unit, expiryDate);
        }
        cursor.close();
        return item;
    }

    public int updatePantryItem(int id, String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiryDate", expiryDate);

        return db.update("PANTRY",values,"id = ?", new String[]{String.valueOf(id)});
    }

    public int deletePantryItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("PANTRY", "id = ?", new String[]{String.valueOf(id)});
    }

    public long addRecipe(String name, String preparation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("preparation", preparation);

        return db.insert("RECIPES", null, values);
    }

    public long addRecipeIngredient(int recipeId, String ingredientName, double requiredQuantity, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("recipeId", recipeId);
        values.put("ingredientName", ingredientName);
        values.put("requiredQuantity", requiredQuantity);
        values.put("unit", unit);

        return db.insert("RECIPE_INGREDIENTS", null, values);
    }

    public List<Recipe> getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Recipe> recipes = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String preparation = cursor.getString(cursor.getColumnIndexOrThrow("preparation"));

                recipes.add(new Recipe(id, name, preparation));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }

    public Recipe getRecipeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE id = ?", new String[]{String.valueOf(id)});

        Recipe recipe = null;

        if (cursor.moveToFirst()) {
            int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String preparation = cursor.getString(cursor.getColumnIndexOrThrow("preparation"));

            recipe = new Recipe(recipeId, name, preparation);
        }
        cursor.close();
        return recipe;
    }

    public List<RecipeIngredient> getRecipeIngredients(int recipeId) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<RecipeIngredient> ingredients = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPE_INGREDIENTS WHERE recipeId = ?", new String[]{String.valueOf(recipeId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                String ingredientName = cursor.getString(cursor.getColumnIndexOrThrow("ingredientName"));

                double requiredQuantity = cursor.getDouble(cursor.getColumnIndexOrThrow("requiredQuantity"));

                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));

                ingredients.add(new RecipeIngredient(id, recipeId, ingredientName, requiredQuantity, unit));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }
}
