package com.aha.dishordersystem.data.network.json;

import com.google.gson.annotations.SerializedName;

public class OrderDishJson {

    @SerializedName("dish_id")
    private int dishId;

    @SerializedName("order_dish_number")
    private int orderDishNumber;

    public int getDishId() {
        return dishId;
    }

    public int getOrderDishNumber() {
        return orderDishNumber;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public void setOrderDishNumber(int orderDishNumber) {
        this.orderDishNumber = orderDishNumber;
    }
}
