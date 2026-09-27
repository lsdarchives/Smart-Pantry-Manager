package com.example.smartpantrymanager.models;

public class Recipe {

    private int id;
    private String name;
    private String preparation;

    public Recipe(int id, String name, String preparation) {
        this.id = id;
        this.name = name;
        this.preparation = preparation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreparation() {
        return preparation;
    }
}
