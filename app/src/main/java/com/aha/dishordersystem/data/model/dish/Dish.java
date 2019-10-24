package com.aha.dishordersystem.data.model.dish;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 菜
 */
public class Dish extends LitePalSupport {

    @Column(nullable = false)
    private String dishName;

    @Column(nullable = false)
    private double dishPrice;

    @Column(nullable = false)
    private Category dishCategory;

    private String dishImageUrl;

    private String dishDetail;


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

    public Category getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(Category dishCategory) {
        this.dishCategory = dishCategory;
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
}
