package com.aha.dishordersystem.data.dish_data_source;

import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.network.json.DishByIdJson;
import com.aha.dishordersystem.data.network.json.DishListJson;

import java.util.List;

import io.reactivex.Observable;

public interface DishDataSource {

    /**
     * 获取全部菜
     * @return
     */
    Observable<DishListJson> getAllDishes();

    /**
     * 存储菜到本地数据库
     * @param dishCategories
     */
    void saveDishToDB(List<DishCategory> dishCategories);

    /**
     * 存储菜到本地数据库
     * @param dishListJson
     */
    void saveDishToDB(DishListJson dishListJson);

}
