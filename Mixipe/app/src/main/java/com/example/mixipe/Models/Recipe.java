package com.example.mixipe.Models;

import java.util.ArrayList;

public class Recipe {
    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public int weightWatcherSmartPoints;
    public String gaps;
    public boolean lowFodmap;
    public int aggregateLikes;
    public double spoonacularScore;
    public double healthScore;
    public String creditsText;
    public String license;
    public String sourceName;
    public double pricePerServing;
    public ArrayList<ExtendedIngredient> extendedIngredients;
    public int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public String image;
    public String imageType;
    public String summary;
    public ArrayList<Object> cuisines;
    public ArrayList<Object> dishTypes;
    public ArrayList<String> diets;
    public ArrayList<Object> occasions;
    public String instructions;
    public ArrayList<AnalyzedInstruction> analyzedInstructions;
    public Object originalId;
    public String spoonacularSourceUrl;


    public Recipe(String title, String image, int servings, int readyInMinutes, int aggregateLikes) {
        this.title = title;
        this.image = image;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.aggregateLikes = aggregateLikes;
    }

    public Recipe() {
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getServings() {return servings;}

    public int getReadyInMinutes() {return readyInMinutes;}

    public int getAggregateLikes() {return aggregateLikes;}
}


