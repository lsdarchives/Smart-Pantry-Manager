package com.example.smartpantrymanager;

import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;
import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {
    public static List<Recipe> findMatchingRecipes(List<Recipe> recipes, List<PantryItem> pantryItems, DatabaseHelper dbHelper) {
        List<Recipe> matchingRecipes = new ArrayList<>();

        if (recipes == null || recipes.isEmpty() || pantryItems == null || dbHelper == null) {
            return matchingRecipes;
        }

        for (Recipe recipe : recipes) {
            if (recipe == null || recipe.getId() <= 0) {
                continue;
            }
            List<RecipeIngredient> requiredIngredients = dbHelper.getRecipeIngredients(recipe.getId());

            if (requiredIngredients == null || requiredIngredients.isEmpty()) {
                continue;
            }
            boolean canMakeRecipe = true;

            for (RecipeIngredient required : requiredIngredients) {
                if (required == null || required.getIngredientName() == null || required.getIngredientName().trim().isEmpty() || required.getUnit() == null || required.getUnit().trim().isEmpty() || required.getRequiredQuantity() <= 0) {
                    canMakeRecipe = false;
                    break;
                }
                double availableQuantity = getAvailableQuantity(required.getIngredientName(), required.getUnit(), pantryItems);
                double requiredQuantity = convertToBaseQuantity(required.getRequiredQuantity(), required.getUnit());

                if (availableQuantity < requiredQuantity) {
                    canMakeRecipe = false;
                    break;
                }
            }
            if (canMakeRecipe) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    private static double getAvailableQuantity(String requiredName, String requiredUnit, List<PantryItem> pantryItems) {
        if (pantryItems == null || pantryItems.isEmpty()) {
            return 0;
        }
        if (requiredName == null || requiredName.trim().isEmpty() || requiredUnit == null || requiredUnit.trim().isEmpty()) {
            return 0;
        }

        String normalizedName = normalizeIngredientName(requiredName);
        String requiredCategory = getUnitCategory(requiredUnit);

        double totalAvailable = 0;

        for (PantryItem item : pantryItems) {
            if (item == null || item.getName() == null || item.getUnit() == null || item.getName().trim().isEmpty() || item.getUnit().trim().isEmpty() || item.getQuantity() <= 0) {
                continue;
            }
            if (!normalizeIngredientName(item.getName()).equals(normalizedName)) {
                continue;
            }
            if (!getUnitCategory(item.getUnit()).equals(requiredCategory)) {
                continue;
            }
            totalAvailable += convertToBaseQuantity(item.getQuantity(), item.getUnit());
        }
        return totalAvailable;
    }
    private static String normalizeIngredientName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        String normalized = name.toLowerCase().trim();

        if (normalized.endsWith("ies")) {
            normalized = normalized.substring(0, normalized.length() - 3) + "y";

        } else if (normalized.endsWith("oes")) {
            normalized = normalized.substring(0, normalized.length() - 2);

        } else if (normalized.endsWith("s") && !normalized.endsWith("ss")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }

        return normalized;
    }

    private static String getUnitCategory(String unit) {
        String normalized = unit.toLowerCase().trim();
        if (normalized.equals("g") || normalized.equals("gram") || normalized.equals("grams") || normalized.equals("kg") || normalized.equals("kilogram") || normalized.equals("kilograms")) {
            return "weight";
        }
        if (normalized.equals("ml") || normalized.equals("milliliter") || normalized.equals("milliliters") || normalized.equals("l") || normalized.equals("liter") || normalized.equals("liters")) {
            return "volume";
        }
        if (normalized.equals("piece") || normalized.equals("pieces") || normalized.equals("item") || normalized.equals("items") || normalized.equals("pc") || normalized.equals("pcs")) {
            return "piece";
        }
        if (normalized.equals("slice") || normalized.equals("slices")) {
            return "slice";
        }
        if (normalized.equals("clove") || normalized.equals("cloves")) {
            return "clove";
        }
        if (normalized.equals("can") || normalized.equals("cans")) {
            return "can";
        }
        return normalized;
    }

    private static double convertToBaseQuantity(double quantity, String unit) {
        String normalized = unit.toLowerCase().trim();

        if (normalized.equals("kg") || normalized.equals("kilogram") || normalized.equals("kilograms")) {
            return quantity * 1000;
        }
        if (normalized.equals("l") || normalized.equals("liter") || normalized.equals("liters")) {
            return quantity * 1000;
        }
        return quantity;
    }
}
