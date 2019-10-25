package com.aha.dishordersystem.app;

import com.aha.dishordersystem.data.DataRepository;
import com.aha.dishordersystem.data.DishDataSource;
import com.aha.dishordersystem.data.DishDataSourceImpl;
import com.aha.dishordersystem.data.OrderDataSource;
import com.aha.dishordersystem.data.OrderDataSourceImpl;
import com.aha.dishordersystem.data.network.api.DishService;
import com.aha.dishordersystem.data.network.api.OrderService;
import com.aha.dishordersystem.util.RetrofitClient;

/**
 * 注入全局数据仓库
 */
public class Injection {

    public static DataRepository provideDataRepository() {
        // 菜单网络API
        DishService dishService = RetrofitClient.getInstance().create(DishService.class);
        // 订单网络API
        OrderService orderService = RetrofitClient.getInstance().create(OrderService.class);
        // 菜单数据源
        DishDataSource dishDataSource = DishDataSourceImpl.getInstance(dishService);
        // 订单数据源
        OrderDataSource orderDataSource = OrderDataSourceImpl.getInstace(orderService);
        // 两个数据源组成一个数据仓库
        return DataRepository.getInstance(dishDataSource, orderDataSource);
    }

}
