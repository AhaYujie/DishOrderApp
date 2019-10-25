package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishListJson {

    private int status;

    @SerializedName("dish_categories")
    private List<DishCategoryJson> dishCategories;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DishCategoryJson> getDishCategories() {
        return dishCategories;
    }

    public void setDishCategories(List<DishCategoryJson> dishCategories) {
        this.dishCategories = dishCategories;
    }

}
