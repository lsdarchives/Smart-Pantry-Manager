package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;
import java.util.List;
import android.content.SharedPreferences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PantryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private PantryAdapter adapter;

    private DatabaseHelper dbHelper;

    private TextView emptyPantryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ThemeManager.applySavedTheme(this);
        setContentView(R.layout.activity_pantry);

        recyclerView = findViewById(R.id.recyclerPantry);
        emptyPantryText = findViewById(R.id.emptyPantryText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        adapter = new PantryAdapter(new java.util.ArrayList<>(), item -> {
                    Intent intent = new Intent(PantryActivity.this, AddEditActivity.class);
                    intent.putExtra("itemId", item.getId());
                    startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        Button addIngredientBtn = findViewById(R.id.addIngredientBtn);

        addIngredientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(PantryActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.navHome).setOnClickListener(view -> {
            startActivity(new Intent(PantryActivity.this, MainActivity.class));
            finish();
        });

        findViewById(R.id.navPantry).setOnClickListener(view -> {
            // Already on Pantry
        });

        findViewById(R.id.navRecipes).setOnClickListener(view -> {
            startActivity(new Intent(PantryActivity.this, SuggestedRecipesActivity.class));
            finish();
        });

        findViewById(R.id.navSettings).setOnClickListener(view -> {
            startActivity(new Intent(PantryActivity.this, SettingsActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();

        SharedPreferences preferences = getSharedPreferences("SmartPantrySettings", MODE_PRIVATE);

        boolean showExpired = preferences.getBoolean("showExpired", true);

        if (!showExpired) {
            pantryItems = removeExpiredItems(pantryItems);
        }

        adapter.updateItems(pantryItems);

        if (pantryItems.isEmpty()) {
            emptyPantryText.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyPantryText.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);
        }
    }

    private List<PantryItem> removeExpiredItems(List<PantryItem> items) {
        List<PantryItem> activeItems = new java.util.ArrayList<>();

        if (items == null) {
            return activeItems;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        format.setLenient(false);

        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        for (PantryItem item : items) {
            String expiryDate = item.getExpiryDate();
            if (expiryDate == null || expiryDate.trim().isEmpty()) {
                activeItems.add(item);
                continue;
            }
            try {
                Date expiry = format.parse(expiryDate);

                if (expiry == null) {
                    activeItems.add(item);
                    continue;
                }
                if (!expiry.before(today.getTime())) {
                    activeItems.add(item);
                }

            } catch (ParseException e) {
                activeItems.add(item);
            }
        }
        return activeItems;
    }
}