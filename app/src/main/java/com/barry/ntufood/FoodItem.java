package com.barry.ntufood;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodItem {

    private List<String> outlet = new ArrayList<>();
    private String category = "", name = "", description = "", image = "", allergens = "", nutrition = "";
    private HashMap<String, Double> price = new HashMap<String, Double>(), additions = new HashMap<String, Double>();
    private List<Map.Entry<String, Double>> priceSorted, additionsSorted;
    private List<String> dietary = new ArrayList<>(), core = new ArrayList<>(), options = new ArrayList<>(), availability = new ArrayList<>();



    public FoodItem() {}

    /**
     * @param outlet
     * @param category
     * @param name
     * @param description
     * @param price
     * @param image
     * @param allergens
     * @param dietary
     * @param additions
     * @param core
     * @param options
     * @param availability
     * @param nutrition
     */
    public FoodItem(List<String> outlet, String category, String name, String description,
                    HashMap<String, Double> price, String image, String allergens, List<String> dietary, HashMap<String, Double> additions,
                    List<String> core, List<String> options, List<String> availability, String nutrition) {

        this.outlet = outlet;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.priceSorted = sortByValue(this.price);
        this.image = image;
        this.allergens = allergens;
        this.dietary = dietary;
        this.additions = additions;
        this.additionsSorted = sortByValue(this.additions);
        this.core = core;
        this.options = options;
        this.availability = availability;
        this.nutrition = nutrition;
    }


    public List<String> getOutlet() {
        return outlet;
    }

    public void setOutlet(List<String> outlet) {
        this.outlet = outlet;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getNutrition() {
        return nutrition.replace(", ","\n");
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public HashMap<String, Double> getPrice() {
        return price;
    }

    public void setPrice(HashMap<String, Double> price) {
        this.price = price;
    }

    public HashMap<String, Double> getAdditions() {
        return additions;
    }

    public void setAdditions(HashMap<String, Double> additions) {
        this.additions = additions;
    }

    public List<Map.Entry<String, Double>> getPriceSorted() {
        return priceSorted;
    }

    public void setPriceSorted(HashMap<String, Double> priceSorted) {
        this.priceSorted = sortByValue(priceSorted);
    }

    public List<Map.Entry<String, Double>> getAdditionsSorted() {
        return additionsSorted;
    }

    public void setAdditionsSorted(HashMap<String, Double> additionsSorted) {
        this.additionsSorted = sortByValue(additionsSorted);
    }

    public List<String> getDietary() {
        return dietary;
    }

    public void setDietary(List<String> dietary) {
        this.dietary = dietary;
    }

    public List<String> getCore() {
        return core;
    }

    public void setCore(List<String> core) {
        this.core = core;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public void setAvailability(List<String> availability) {
        this.availability = availability;
    }

    public String getLowestPrice() {
        if (price.size() > 1) {
            return "From £" + String.format("%.2f", priceSorted.get(0).getValue());
        } else {
            return "£" + String.format("%.2f", priceSorted.get(0).getValue());
        }
    }

    public String getDietaryAsString() {
        String returnList = "";

        for (String currItem : dietary) {

            returnList += currItem + " ";
        }
        return returnList;
    }

    private List<Map.Entry<String, Double>> sortByValue(HashMap<String, Double> input){
        List<Map.Entry<String, Double>> inputList = new LinkedList<Map.Entry<String, Double> >(input.entrySet());
        Collections.sort(inputList, new AscPriceComparator());

        return inputList;
    }
}
