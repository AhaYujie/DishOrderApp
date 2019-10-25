package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishCategoryJson {

    @SerializedName("category")
    private String dishCategory;

    private List<DishJson> dishes;

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public List<DishJson> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishJson> dishes) {
        this.dishes = dishes;
    }
}
