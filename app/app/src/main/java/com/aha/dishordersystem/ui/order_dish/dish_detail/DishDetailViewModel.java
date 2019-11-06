package com.aha.dishordersystem.ui.order_dish.dish_detail;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.ui.order_dish.OrderDishDishesItemViewModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class DishDetailViewModel extends BaseViewModel {

    private int dishServerId;

    private ObservableField<String> dishImageUrl = new ObservableField<>("http://aha.jpg");

    private ObservableField<String> dishName = new ObservableField<>("菜名");

    private ObservableField<String> dishPrice = new ObservableField<>("0.00");

    private ObservableField<Integer> reduceButtonVisibility = new ObservableField<>(View.INVISIBLE);

    private ObservableField<String> orderDishNumber = new ObservableField<>("0");

    private ObservableField<Integer> orderDishNumberVisibility = new ObservableField<>(View.INVISIBLE);

    private ObservableField<String> dishDetail = new ObservableField<>("简介");

    private SingleLiveEvent<Integer> orderDishNumberChange = new SingleLiveEvent<>();

    private BindingCommand backButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    private BindingCommand reduceButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click reduce button
            Log.d(MyApplication.getTAG(), "click dish detail reduce button");
            orderDishNumber.set(String.valueOf(Integer.valueOf(orderDishNumber.get()) - 1));
            changeOrderDishNumber();
        }
    });

    private BindingCommand addButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click add button
            Log.d(MyApplication.getTAG(), "click dish detail add button");
            orderDishNumber.set(String.valueOf(Integer.valueOf(orderDishNumber.get()) + 1));
            changeOrderDishNumber();
        }
    });

    /**
     * 改变订单的菜数量
     */
    private void changeOrderDishNumber() {
        orderDishNumberChange.setValue(Integer.valueOf(orderDishNumber.get()));
        Messenger.getDefault().send(new OrderDishDishesItemViewModel.MessengerHelper(dishServerId,
                orderDishNumber.get()), OrderDishDishesItemViewModel.TOKEN_ORDER_DISH_NUMBER_CHANGE);
    }

    public DishDetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 初始化数据
     * @param dishData
     */
    public void initData(Bundle dishData) {
        dishServerId = dishData.getInt("dish_server_id");
        dishImageUrl.set(dishData.getString("dish_image_url"));
        dishName.set(dishData.getString("dish_name"));
        dishPrice.set(dishData.getString("dish_price"));
        dishDetail.set(dishData.getString("dish_detail"));
        orderDishNumber.set(dishData.getString("order_dish_number"));
        if (!"0".equals(orderDishNumber.get())) {
            reduceButtonVisibility.set(View.VISIBLE);
            orderDishNumberVisibility.set(View.VISIBLE);
        }
        Log.d(MyApplication.getTAG(), "dish number: " + dishData.getString("order_dish_number"));
    }

    public SingleLiveEvent<Integer> getOrderDishNumberChange() {
        return orderDishNumberChange;
    }

    public BindingCommand getBackButtonClick() {
        return backButtonClick;
    }

    public BindingCommand getAddButtonClick() {
        return addButtonClick;
    }

    public BindingCommand getReduceButtonClick() {
        return reduceButtonClick;
    }

    public ObservableField<String> getDishDetail() {
        return dishDetail;
    }

    public ObservableField<Integer> getOrderDishNumberVisibility() {
        return orderDishNumberVisibility;
    }

    public ObservableField<Integer> getReduceButtonVisibility() {
        return reduceButtonVisibility;
    }

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public ObservableField<String> getDishImageUrl() {
        return dishImageUrl;
    }

    public ObservableField<String> getDishName() {
        return dishName;
    }

    public ObservableField<String> getDishPrice() {
        return dishPrice;
    }
}
