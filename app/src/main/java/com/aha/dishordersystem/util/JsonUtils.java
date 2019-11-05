package com.aha.dishordersystem.util;

import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.model.dish.Dish;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.data.network.json.DishCategoryJson;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.data.network.json.OrderDishJson;
import com.aha.dishordersystem.data.network.json.OrderDishesJson;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * HistoryOrder 转换成 OrderDishesJson
     * @param historyOrder
     * @return
     */
    public static OrderDishesJson historyOrderToOrderDishesJson(HistoryOrder historyOrder) {
        OrderDishesJson orderDishesJson = new OrderDishesJson();
        for (OrderDish orderDish : historyOrder.getOrderDishes()) {
            OrderDishJson orderDishJson = new OrderDishJson();
            orderDishJson.setDishId(orderDish.getDishServerId());
            orderDishJson.setOrderDishNumber(orderDish.getDishNumber());
            orderDishesJson.getOrderDishes().add(orderDishJson);
        }
        orderDishesJson.setOrderDishTotalNumber(historyOrder.getOrderDishNumber());
        orderDishesJson.setOrderTotalPrice(historyOrder.getOrderTotalPrice());
        return  orderDishesJson;
    }

    /**
     * DishListJson 转换为 List<DishCategory>
     * @param dishListJson
     * @return
     */
    public static List<DishCategory> dishListJsonToDishCategories(DishListJson dishListJson) {
        List<DishCategory> dishCategories = new ArrayList<>();
        for (DishCategoryJson dishCategoryJson : dishListJson.getDishCategories()) {
            dishCategories.add(dishCategoryJsonToDishCategory(dishCategoryJson));
        }
        return dishCategories;
    }

    /**
     * DishCategoryJson 转换为 DishCategory
     * @param dishCategoryJson
     * @return
     */
    public static DishCategory dishCategoryJsonToDishCategory(DishCategoryJson dishCategoryJson) {
        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(dishCategoryJson.getDishCategory());
        for (DishJson dishJson : dishCategoryJson.getDishes()) {
            Dish dish = dishJsonToDish(dishJson);
            dish.setDishCategory(dishCategory);
            dishCategory.getDishes().add(dish);
        }
        return dishCategory;
    }

    /**
     * dishJson 转换为 Dish
     * @param dishJson
     * @return
     */
    public static Dish dishJsonToDish(DishJson dishJson) {
        Dish dish = new Dish();
        dish.setServerId(dishJson.getDishId());
        dish.setDishName(dishJson.getDishName());
        dish.setDishDetail(dishJson.getDishDetail());
        dish.setDishPrice(dishJson.getDishPrice());
        dish.setDishImageUrl(dishJson.getDishImageUrl());
        return dish;
    }

    /**
     * List<DishCategory> 转换为 DishListJson
     * @param dishCategories
     * @return
     */
    public static DishListJson dishCategoriesToDishListJson(List<DishCategory> dishCategories) {
        DishListJson dishListJson = new DishListJson();
        dishListJson.setDishCategories(new ArrayList<DishCategoryJson>());
        for (DishCategory dishCategory : dishCategories) {
            dishListJson.getDishCategories().add(dishCategoryToDishCategoryJson(dishCategory));
        }
        if (dishListJson.getDishCategories().isEmpty()) {
            dishListJson.setStatus(DishListJson.STATUS_ERROR);
        }
        else {
            dishListJson.setStatus(DishListJson.STATUS_SUCCESS);
        }
        return dishListJson;
    }

    /**
     * DishCategory 转换成 DishCategoryJson
     * @param dishCategory
     * @return
     */
    public static DishCategoryJson dishCategoryToDishCategoryJson(DishCategory dishCategory) {
        DishCategoryJson dishCategoryJson = new DishCategoryJson();
        dishCategoryJson.setDishCategory(dishCategory.getCategoryName());
        dishCategoryJson.setDishes(new ArrayList<DishJson>());
        for (Dish dish : dishCategory.getDishes()) {
            dishCategoryJson.getDishes().add(dishToDishJson(dish));
        }
        return dishCategoryJson;
    }

    /**
     * Dish 转换成 DishJson
     * @param dish
     * @return
     */
    public static DishJson dishToDishJson(Dish dish) {
        DishJson dishJson = new DishJson();
        dishJson.setDishId(dish.getServerId());
        dishJson.setDishName(dish.getDishName());
        dishJson.setDishPrice(dish.getDishPrice());
        dishJson.setDishDetail(dish.getDishDetail());
        dishJson.setDishImageUrl(dish.getDishImageUrl());
        return dishJson;
    }

}
