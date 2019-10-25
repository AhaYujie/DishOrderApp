package com.aha.dishordersystem.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.network.json.DishByIdJson;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.data.network.json.PayOrderJson;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

public class DataRepository extends BaseModel implements DishDataSource, OrderDataSource{

    private static volatile DataRepository INSTANCE = null;

    private final DishDataSource dishDataSource;

    private final OrderDataSource orderDataSource;

    private DataRepository(DishDataSource dishDataSource, OrderDataSource orderDataSource) {
        this.dishDataSource = dishDataSource;
        this.orderDataSource = orderDataSource;
    }

    public static DataRepository getInstance(@NonNull DishDataSource dishDataSource,
                                             @NonNull OrderDataSource orderDataSource) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(dishDataSource, orderDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destoryInstance() {
        INSTANCE = null;
    }

    /**
     * 从本地数据库获取全部菜
     *
     * @return
     */
    @Override
    public List<DishCategory> getAllDishesFromLocal() {
        return dishDataSource.getAllDishesFromLocal();
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishCategories
     */
    @Override
    public void saveDishToDB(List<DishCategory> dishCategories) {
        dishDataSource.saveDishToDB(dishCategories);
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishCategory
     */
    @Override
    public void saveDishToDB(DishCategory dishCategory) {
        dishDataSource.saveDishToDB(dishCategory);
    }

    /**
     * 从服务器获取菜单列表
     *
     * @return
     */
    @Override
    public Observable<DishListJson> getDishListFromServer() {
        return dishDataSource.getDishListFromServer();
    }

    /**
     * 从服务器按照id获取菜单
     *
     * @param idList
     * @return
     */
    @Override
    public Observable<DishByIdJson> getDishByIdFromServer(List<Integer> idList) {
        return dishDataSource.getDishByIdFromServer(idList);
    }

    /**
     * 从本地数据库获取全部订单
     *
     * @return
     */
    @Override
    public List<HistoryOrder> getAllOrdersFromLocal() {
        return orderDataSource.getAllOrdersFromLocal();
    }

    /**
     * 存储订单到本地数据库
     *
     * @param historyOrders
     */
    @Override
    public void saveOrderToDB(List<HistoryOrder> historyOrders) {
        orderDataSource.saveOrderToDB(historyOrders);
    }

    /**
     * 存储订单到本地数据库
     *
     * @param historyOrder
     */
    @Override
    public void saveOrderToDB(HistoryOrder historyOrder) {
        orderDataSource.saveOrderToDB(historyOrder);
    }

    /**
     * 支付订单
     *
     * @param dishJsonList 订单的菜
     * @return
     */
    @Override
    public Observable<PayOrderJson> payOrder(List<DishJson> dishJsonList) {
        return orderDataSource.payOrder(dishJsonList);
    }
}
