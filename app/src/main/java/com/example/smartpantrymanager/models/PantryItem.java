package com.example.smartpantrymanager.models;

public class PantryItem {
    private int id;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    // Constructor
    public PantryItem(int id, String name, double quantity, String unit, String expiryDate){
        this.id= this.id;
        this.name= this.name;
        this.quantity= this.quantity;
        this.unit= this.unit;
        this.expiryDate= this.expiryDate;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getQuantity() {
        return quantity;
    }
    public String getUnit() {
        return unit;
    }
    public String getExpiryDate() {
        return expiryDate;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
