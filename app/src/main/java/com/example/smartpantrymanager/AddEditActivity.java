package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AddEditActivity extends AppCompatActivity {

    private EditText ingredientNameInput;
    private EditText quantityInput;
    private EditText unitInput;
    private EditText expiryDateInput;
    private Button saveIngredientBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit);

        ingredientNameInput = findViewById(R.id.ingredientNameInput);
        quantityInput = findViewById(R.id.quantityInput);
        unitInput = findViewById(R.id.unitInput);
        expiryDateInput = findViewById(R.id.expiryDateInput);

        saveIngredientBtn = findViewById(R.id.saveIngredientBtn);

        saveIngredientBtn.setOnClickListener(view -> {
            saveIngredient();
        });
    }

    // Save method
    private void saveIngredient(){
        String name = ingredientNameInput.getText().toString().trim();
        String quantityText = quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiryDate = expiryDateInput.getText().toString().trim();
    }



}