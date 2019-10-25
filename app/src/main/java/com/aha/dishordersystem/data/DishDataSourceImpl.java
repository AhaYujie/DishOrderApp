package com.aha.dishordersystem.data;

import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.dao.DishDao;
import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.network.api.DishService;
import com.aha.dishordersystem.data.network.json.DishByIdJson;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.util.HttpsUtils;

import java.util.List;

import io.reactivex.Observable;

public class DishDataSourceImpl implements DishDataSource{

    private DishService dishService;

    private static volatile DishDataSourceImpl INSTANCE = null;

    public static DishDataSource getInstance(DishService dishService) {
        if (INSTANCE == null) {
            synchronized (DishDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DishDataSourceImpl(dishService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    private DishDataSourceImpl(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * 从本地数据库获取全部菜
     *
     * @return
     */
    @Override
    public List<DishCategory> getAllDishesFromLocal() {
        return DishDao.getAllDishes();
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishCategories
     */
    @Override
    public void saveDishToDB(List<DishCategory> dishCategories) {
        DishDao.save(dishCategories);
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishCategory
     */
    @Override
    public void saveDishToDB(DishCategory dishCategory) {
        DishDao.save(dishCategory);
    }

    /**
     * 从服务器获取菜单列表
     *
     * @return
     */
    @Override
    public Observable<DishListJson> getDishListFromServer() {
        return dishService.getAllDishes(HttpsUtils.TOKEN);
    }

    /**
     * 从服务器按照id获取菜单
     *
     * @param idList
     * @return
     */
    @Override
    public Observable<DishByIdJson> getDishByIdFromServer(List<Integer> idList) {
        return dishService.getDishesById(HttpsUtils.TOKEN, idList);
    }
}
