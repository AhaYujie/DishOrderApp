package com.aha.dishordersystem.data.db.dao;

import android.util.Log;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.model.dish.Dish;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DishDao {

    /**
     * 从数据库获取全部菜
     * @return
     */
    public static List<DishCategory> getAllDishes() {
        List<DishCategory> dishCategoryList = LitePal.findAll(DishCategory.class, true);
        return dishCategoryList;
    }

    /**
     * 存储菜进数据库
     * @param dishCategories
     */
    public static void save(List<DishCategory> dishCategories) {
        for (DishCategory dishCategory : dishCategories) {
            save(dishCategory);
        }
    }

    /**
     * 存储菜进数据库
     * @param dishCategory
     */
    public static void save(DishCategory dishCategory) {
        List<Dish> dishList = new ArrayList<>(dishCategory.getDishes());
        LitePal.saveAll(dishList);
        dishCategory.setDishes(dishList);
        dishCategory.save();
    }

}
