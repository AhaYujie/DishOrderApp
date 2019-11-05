package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDishesJson {

    private String token;

    @SerializedName("order_dishes")
    private List<OrderDishJson> orderDishes = new ArrayList<>();

    @SerializedName("order_dish_total_number")
    private int orderDishTotalNumber;

    @SerializedName("order_total_price")
    private double orderTotalPrice;

    public int getOrderDishTotalNumber() {
        return orderDishTotalNumber;
    }

    public void setOrderDishTotalNumber(int orderDishTotalNumber) {
        this.orderDishTotalNumber = orderDishTotalNumber;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public List<OrderDishJson> getOrderDishes() {
        return orderDishes;
    }

    public String getToken() {
        return token;
    }

    public void setOrderDishes(List<OrderDishJson> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
