package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

public class DishJson {

    @SerializedName("dish_id")
    private int dishId;

    @SerializedName("dish_name")
    private String dishName;

    @SerializedName("dish_price")
    private double dishPrice;

    @SerializedName("dish_detail")
    private String dishDetail;

    @SerializedName("dish_img_url")
    private String dishImageUrl;

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(String dishDetail) {
        this.dishDetail = dishDetail;
    }

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

}
