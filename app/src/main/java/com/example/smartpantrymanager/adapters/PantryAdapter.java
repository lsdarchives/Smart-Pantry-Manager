package com.example.smartpantrymanager.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.models.PantryItem;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;

    public PantryAdapter(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        holder.txtIngredientName.setText(item.getName());

        holder.txtQuantity.setText(
                item.getQuantity() + " " + item.getUnit()
        );

        holder.txtExpiryDate.setText(
                "Expires: " + item.getExpiryDate()
        );
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName;
        TextView txtQuantity;
        TextView txtExpiryDate;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredientName = itemView.findViewById(R.id.txtIngredientName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtExpiryDate = itemView.findViewById(R.id.txtExpiryDate);
        }
    }
}
