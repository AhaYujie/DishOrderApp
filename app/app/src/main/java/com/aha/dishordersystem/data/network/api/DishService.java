package com.aha.dishordersystem.data.network.api;

import com.aha.dishordersystem.data.network.json.DishByIdJson;
import com.aha.dishordersystem.data.network.json.DishListJson;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import io.reactivex.Observable;

public interface DishService {

    @FormUrlEncoded
    @POST("dish_list")
    Observable<DishListJson> getAllDishes(@Field("token") String token);

    @FormUrlEncoded
    @POST("dishes_by_id")
    Observable<DishByIdJson> getDishesById(@Field("token") String token,
                                           @Field("dish_id") List<Integer> idList);

}
