package com.aha.dishordersystem.data.db.dao;

import android.util.Log;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.dish.Dish;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    /**
     * 从数据库获取所有订单
     * @return
     */
    public static List<HistoryOrder> getAllOrders() {
        List<HistoryOrder> historyOrders = LitePal.findAll(HistoryOrder.class, true);
        for (HistoryOrder historyOrder : historyOrders) {
            List<OrderDish> orderDishes = new ArrayList<>();
            for (OrderDish orderDish : historyOrder.getOrderDishes()) {
                Dish dish = LitePal.where("serverId = ?", String.valueOf(orderDish.getDishServerId()))
                                   .findFirst(Dish.class);
                orderDish.setDish(dish);
                orderDishes.add(orderDish);
            }
            historyOrder.setOrderDishes(orderDishes);
        }
        return historyOrders;
    }

    /**
     * 存储订单到数据库
     * @param historyOrder
     */
    public static void save(HistoryOrder historyOrder) {
        try {
            List<OrderDish> orderDishes = new ArrayList<>(historyOrder.getOrderDishes());
            for (OrderDish orderDish : orderDishes) {
                save(orderDish);
            }
            historyOrder.setOrderDishes(orderDishes);
            historyOrder.save();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void save(OrderDish orderDish) {
        try {
            orderDish.save();
            Dish dish = LitePal.where("serverId = ?", String.valueOf(orderDish.getDishServerId()))
                                .findFirst(Dish.class);
            if (dish != null) {
                orderDish.setDish(dish);
                orderDish.save();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
