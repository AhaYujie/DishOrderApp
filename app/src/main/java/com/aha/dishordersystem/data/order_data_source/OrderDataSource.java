package com.aha.dishordersystem.data.order_data_source;

import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.OrderDishesJson;
import com.aha.dishordersystem.data.network.json.PayOrderResponseJson;

import java.util.List;

import io.reactivex.Observable;

public interface OrderDataSource {

    /**
     * 获取全部订单
     * @return
     */
    Observable<List<HistoryOrder>> getAllOrders();

    /**
     * 存储订单到本地数据库
     * @param historyOrders
     */
    void saveOrderToDB(List<HistoryOrder> historyOrders);

    /**
     * 存储订单到本地数据库
     * @param historyOrder
     */
    void saveOrderToDB(HistoryOrder historyOrder);

    /**
     * 支付订单
     * @param orderDishesJson 订单的菜
     * @return
     */
    Observable<PayOrderResponseJson> payOrder(OrderDishesJson orderDishesJson);

    /**
     * 删除订单
     * @param historyOrder
     */
    void deleteOrder(HistoryOrder historyOrder);

}
