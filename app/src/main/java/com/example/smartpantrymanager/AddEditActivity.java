package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;

public class AddEditActivity extends AppCompatActivity {

    private EditText ingredientNameInput;
    private EditText quantityInput;
    private EditText unitInput;
    private EditText expiryDateInput;
    private Button saveIngredientBtn;
    private Button deleteIngredientBtn;

    private DatabaseHelper dbHelper;
    private int itemId = -1;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ThemeManager.applySavedTheme(this);
        setContentView(R.layout.activity_add_edit);

        dbHelper = new DatabaseHelper(this);

        itemId = getIntent().getIntExtra("itemId", -1);

        if (itemId != -1) {
            editMode = true;
            loadItemForEditing();
        }

        ingredientNameInput = findViewById(R.id.ingredientNameInput);
        quantityInput = findViewById(R.id.quantityInput);
        unitInput = findViewById(R.id.unitInput);
        SharedPreferences preferences = getSharedPreferences("SmartPantrySettings", MODE_PRIVATE);
        String defaultUnit = preferences.getString("defaultUnit", "g");

        if (!editMode && unitInput.getText().toString().trim().isEmpty()) {
            unitInput.setText(defaultUnit);
        }
        expiryDateInput = findViewById(R.id.expiryDateInput);
        saveIngredientBtn = findViewById(R.id.saveIngredientBtn);

        saveIngredientBtn.setOnClickListener(view -> {
            saveIngredient();
        });
    }

    private void loadItemForEditing() {

        PantryItem item = dbHelper.getPantryItemById(itemId);

        if (item != null) {
            ingredientNameInput.setText(item.getName());
            quantityInput.setText(String.valueOf(item.getQuantity()));
            unitInput.setText(item.getUnit());
            expiryDateInput.setText(item.getExpiryDate());
        }
    }

    // Saves a new ingredient to the database
    private void saveIngredient() {

        String name = ingredientNameInput.getText().toString().trim();
        String quantityText = quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiryDate = expiryDateInput.getText().toString().trim();

        double quantity;

        // Validate ingredient name
        if (name.isEmpty()) {
            ingredientNameInput.setError("Enter an ingredient name!");
            ingredientNameInput.requestFocus();
            return;
        }

        // Prevent excessively long ingredient names
        if (name.length() > 50) {
            ingredientNameInput.setError("Ingredient name is too long!");
            ingredientNameInput.requestFocus();
            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {
            quantityInput.setError("Enter a quantity!");
            quantityInput.requestFocus();
            return;
        }

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            quantityInput.setError("Enter a valid number!");
            quantityInput.requestFocus();
            return;
        }

        // Prevent invalid mathematical values
        if (Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            quantityInput.setError("Enter a valid quantity!");
            quantityInput.requestFocus();
            return;
        }

        // Quantity must be positive
        if (quantity <= 0) {
            quantityInput.setError("Quantity must be greater than 0!");
            quantityInput.requestFocus();
            return;
        }

        // Prevent unrealistic values
        if (quantity > 1_000) {
            quantityInput.setError("Quantity is too large!");
            quantityInput.requestFocus();
            return;
        }

        // Validate unit
        if (unit.isEmpty()) {
            unitInput.setError("Enter a unit!");
            unitInput.requestFocus();
            return;
        }

        if (unit.length() > 20) {
            unitInput.setError("Unit is too long!");
            unitInput.requestFocus();
            return;
        }

        if (editMode) {
            int result = dbHelper.updatePantryItem(itemId, name, quantity, unit, expiryDate);

            if (result > 0) {
                finish();
            }

        } else {
            long result = dbHelper.addPantryItem(name, quantity, unit, expiryDate);

            if (result != -1) {
                finish();
            }
        }
    }

}