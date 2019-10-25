package com.aha.dishordersystem.data.network.api;

import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.PayOrderJson;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {

    @FormUrlEncoded
    @POST("pay_order")
    Observable<PayOrderJson> payOrder(@Field("token") String token,
                                      @Field("order_dishes")List<DishJson> dishJsonList);

}
