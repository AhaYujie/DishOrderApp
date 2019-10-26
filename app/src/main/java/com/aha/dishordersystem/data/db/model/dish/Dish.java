package com.aha.dishordersystem.data.db.model.dish;

import androidx.annotation.NonNull;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 菜
 */
public class Dish extends LitePalSupport {

    @Column(unique = true)
    private int serverId;   // 服务器数据库id

    @Column(nullable = false)
    private String dishName;

    @Column(nullable = false)
    private double dishPrice;

    private String dishImageUrl;

    private String dishDetail;

    @Column(nullable = false)
    private DishCategory dishCategory;

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

    public String getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(String dishDetail) {
        this.dishDetail = dishDetail;
    }

    @NonNull
    @Override
    public String toString() {
        return "server_id: " + serverId + ", " +
                "dish_name: " + dishName + ", " +
                "dish_price: " + dishPrice + ", " +
                "dish_image_url: " + dishImageUrl;
    }
}
