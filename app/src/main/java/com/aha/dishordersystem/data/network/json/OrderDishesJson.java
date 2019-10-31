package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDishesJson {

    private String token;

    @SerializedName("order_dishes")
    private List<OrderDishJson> orderDishes = new ArrayList<>();

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
