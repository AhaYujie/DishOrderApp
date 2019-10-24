package com.aha.dishordersystem.data.model.order;

import com.aha.dishordersystem.data.model.dish.Dish;

import org.litepal.annotation.Column;

/**
 * 订单菜
 */
public class OrderDish {

    @Column(nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private int dishNumber;

}
