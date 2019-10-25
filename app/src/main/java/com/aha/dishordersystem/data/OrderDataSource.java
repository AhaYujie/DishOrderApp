package com.aha.dishordersystem.data;

import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.PayOrderJson;

import java.util.List;

import io.reactivex.Observable;

public interface OrderDataSource {

    /**
     * 从本地数据库获取全部订单
     * @return
     */
    List<HistoryOrder> getAllOrdersFromLocal();

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
     * @param dishJsonList 订单的菜
     * @return
     */
    Observable<PayOrderJson> payOrder(List<DishJson> dishJsonList);

}
