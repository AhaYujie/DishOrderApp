package com.aha.dishordersystem.data;

import com.aha.dishordersystem.data.db.dao.OrderDao;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.network.api.OrderService;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.PayOrderJson;
import com.aha.dishordersystem.util.HttpsUtils;

import java.util.List;

import io.reactivex.Observable;

public class OrderDataSourceImpl implements OrderDataSource {

    private OrderService orderService;

    private static volatile OrderDataSourceImpl INSTANCE = null;

    public static OrderDataSource getInstace(OrderService orderService) {
        if (INSTANCE == null) {
            synchronized (OrderDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderDataSourceImpl(orderService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    private OrderDataSourceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 从本地数据库获取全部订单
     *
     * @return
     */
    @Override
    public List<HistoryOrder> getAllOrdersFromLocal() {
        return OrderDao.getAllOrders();
    }

    /**
     * 存储订单到本地数据库
     *
     * @param historyOrders
     */
    @Override
    public void saveOrderToDB(List<HistoryOrder> historyOrders) {
        OrderDao.save(historyOrders);
    }

    /**
     * 存储订单到本地数据库
     *
     * @param historyOrder
     */
    @Override
    public void saveOrderToDB(HistoryOrder historyOrder) {
        OrderDao.save(historyOrder);
    }

    /**
     * 支付订单
     *
     * @param dishJsonList 订单的菜
     * @return
     */
    @Override
    public Observable<PayOrderJson> payOrder(List<DishJson> dishJsonList) {
        return orderService.payOrder(HttpsUtils.TOKEN, dishJsonList);
    }
}
