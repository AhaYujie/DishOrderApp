package com.aha.dishordersystem.data.db.model.order;

import com.aha.dishordersystem.data.db.model.dish.Dish;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 订单菜
 */
public class OrderDish extends LitePalSupport {

    @Column(nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private int dishNumber;

}
