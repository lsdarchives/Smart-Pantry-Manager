package com.example.smartpantrymanager.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.models.PantryItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {
    private List<PantryItem> pantryItems;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    public PantryAdapter(List<PantryItem> pantryItems, OnItemClickListener listener) {
        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        holder.txtIngredientName.setText(item.getName());
        holder.txtQuantity.setText(item.getQuantity() + " " + item.getUnit());

        String expiryDate = item.getExpiryDate();

        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            holder.txtExpiryDate.setText("No expiry date");
        } else {
            SharedPreferences preferences = holder.itemView.getContext().getSharedPreferences("SmartPantrySettings", Context.MODE_PRIVATE);
            boolean expiryAlerts = preferences.getBoolean("expiryAlerts", true);
            int warningDays = preferences.getInt("expiryDays", 7);

            if (!expiryAlerts) {
                holder.txtExpiryDate.setText("Expires: " + expiryDate);
            } else {
                String expiryStatus = getExpiryStatus(expiryDate, warningDays);

                holder.txtExpiryDate.setText(expiryStatus);
            }
        }
        holder.itemView.setOnClickListener(view -> listener.onItemClick(item));
    }

    private String getExpiryStatus(String expiryDate, int warningDays) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            format.setLenient(false);

            Date expiry = format.parse(expiryDate);

            if (expiry == null) {
                return "Expires: " + expiryDate;
            }

            Calendar today = Calendar.getInstance();
            Calendar expiryCalendar = Calendar.getInstance();

            today.setTime(new Date());
            expiryCalendar.setTime(expiry);

            long difference = expiryCalendar.getTimeInMillis() - today.getTimeInMillis();
            long daysUntilExpiry = difference / (1000 * 60 * 60 * 24);

            if (daysUntilExpiry < 0) {
                return "Expired: " + expiryDate;
            }
            if (daysUntilExpiry == 0) {
                return "Expires today";
            }
            if (daysUntilExpiry <= warningDays) {
                return "Expires soon: " + expiryDate;
            }
            return "Expires: " + expiryDate;

        } catch (ParseException e) {
            return "Expires: " + expiryDate;
        }
    }
    public void updateItems(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
        notifyDataSetChanged();
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
