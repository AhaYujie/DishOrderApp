package com.aha.dishordersystem.data.db.dao;

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
        return LitePal.findAll(HistoryOrder.class);
    }

    /**
     * 存储订单到数据库
     * @param historyOrders
     */
    public static void save(List<HistoryOrder> historyOrders) {
        for (HistoryOrder historyOrder : historyOrders) {
            save(historyOrder);
        }
    }

    /**
     * 存储订单到数据库
     * @param historyOrder
     */
    public static void save(HistoryOrder historyOrder) {
        List<OrderDish> orderDishes = new ArrayList<>(historyOrder.getOrderDishes());
        LitePal.saveAll(orderDishes);
        historyOrder.setOrderDishes(orderDishes);
        historyOrder.save();
    }

}
