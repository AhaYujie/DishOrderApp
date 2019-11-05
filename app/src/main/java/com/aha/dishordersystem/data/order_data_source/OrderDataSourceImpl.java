package com.aha.dishordersystem.data.order_data_source;

import android.util.Log;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.dao.OrderDao;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.network.api.OrderService;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.OrderDishesJson;
import com.aha.dishordersystem.data.network.json.PayOrderResponseJson;
import com.aha.dishordersystem.util.HttpsUtils;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
     * 获取全部订单
     *
     * @return
     */
    @Override
    public Observable<List<HistoryOrder>> getAllOrders() {
        return Observable.create(new ObservableOnSubscribe<List<HistoryOrder>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryOrder>> emitter) throws Exception {
                try {
                    List<HistoryOrder> historyOrders = OrderDao.getAllOrders();
                    emitter.onNext(historyOrders);
                }
                catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
                finally {
                    emitter.onComplete();
                }
            }
        });
    }


    /**
     * 存储订单到本地数据库
     *
     * @param historyOrders
     */
    @Override
    public void saveOrderToDB(List<HistoryOrder> historyOrders) {
        for (HistoryOrder historyOrder : historyOrders) {
            saveOrderToDB(historyOrder);
        }
    }

    /**
     * 存储订单到本地数据库
     *
     * @param historyOrder
     */
    @Override
    public void saveOrderToDB(final HistoryOrder historyOrder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(MyApplication.getTAG(), "prev: " + OrderDao.getAllOrders().size());
                OrderDao.save(historyOrder);
                Log.d(MyApplication.getTAG(), "save order thread: " + Thread.currentThread().getName());
                Log.d(MyApplication.getTAG(), "now: " + OrderDao.getAllOrders().size());
            }
        }).start();
    }

    /**
     * 支付订单
     *
     * @param orderDishesJson 订单的菜
     * @return
     */
    @Override
    public Observable<PayOrderResponseJson> payOrder(OrderDishesJson orderDishesJson) {
        orderDishesJson.setToken(HttpsUtils.TOKEN);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(orderDishesJson));
        Log.d(MyApplication.getTAG(), "request: " + new Gson().toJson(orderDishesJson));
        return orderService.payOrder(requestBody);
    }

    /**
     * 删除订单
     *
     * @param historyOrder
     */
    @Override
    public void deleteOrder(HistoryOrder historyOrder) {
        OrderDao.delete(historyOrder);
    }
}
