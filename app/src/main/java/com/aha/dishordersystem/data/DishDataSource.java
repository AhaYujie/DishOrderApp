package com.aha.dishordersystem.data;

import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.network.json.DishByIdJson;
import com.aha.dishordersystem.data.network.json.DishListJson;

import java.util.List;

import io.reactivex.Observable;

public interface DishDataSource {

    /**
     * 从本地数据库获取全部菜
     * @return
     */
    List<DishCategory> getAllDishesFromLocal();

    /**
     * 存储菜到本地数据库
     * @param dishCategories
     */
    void saveDishToDB(List<DishCategory> dishCategories);

    /**
     * 存储菜到本地数据库
     * @param dishCategory
     */
    void saveDishToDB(DishCategory dishCategory);

    /**
     * 从服务器获取菜单列表
     * @return
     */
    Observable<DishListJson> getDishListFromServer();

    /**
     * 从服务器按照id获取菜单
     * @param idList
     * @return
     */
    Observable<DishByIdJson> getDishByIdFromServer(List<Integer> idList);

}
