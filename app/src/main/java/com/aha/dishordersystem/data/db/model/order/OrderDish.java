package com.aha.dishordersystem.data.db.model.order;

import androidx.annotation.NonNull;

import com.aha.dishordersystem.data.db.model.dish.Dish;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 订单菜
 */
public class OrderDish extends LitePalSupport {

    @Column(nullable = false)
    private int dishServerId;

    @Column(nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private int dishNumber;

    public int getDishServerId() {
        return dishServerId;
    }

    public void setDishServerId(int dishServerId) {
        this.dishServerId = dishServerId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "dish_number: " + dishNumber + ", " +
                "dish: " + dish;
    }
}
