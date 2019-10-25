package com.aha.dishordersystem.data.db.model.order;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class HistoryOrder extends LitePalSupport {

    @Column(nullable = false)
    private Date orderCreateTime;

    @Column(nullable = false)
    private int orderIsFinish;  // 订单完成标记(0为未完成, 1为已完成)

    @Column(nullable = false)
    private int orderDishNumber;

    @Column(nullable = false)
    private double orderTotalPrice;

    @Column(nullable = false)
    private List<OrderDish> orderDishes = new ArrayList<>();

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getOrderIsFinish() {
        return orderIsFinish;
    }

    public void setOrderIsFinish(int orderIsFinish) {
        this.orderIsFinish = orderIsFinish;
    }

    public int getOrderDishNumber() {
        return orderDishNumber;
    }

    public void setOrderDishNumber(int orderDishNumber) {
        this.orderDishNumber = orderDishNumber;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }
}
