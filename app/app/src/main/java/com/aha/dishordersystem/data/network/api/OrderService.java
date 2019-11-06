package com.aha.dishordersystem.data.network.api;

import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.PayOrderResponseJson;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderService {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("pay_order")
    Observable<PayOrderResponseJson> payOrder(@Body RequestBody requestBody);

}
