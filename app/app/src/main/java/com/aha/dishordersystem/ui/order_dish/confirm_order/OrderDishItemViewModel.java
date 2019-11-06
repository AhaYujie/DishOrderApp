package com.aha.dishordersystem.ui.order_dish.confirm_order;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.ui.order_dish.OrderDishDishesItemViewModel;
import com.aha.dishordersystem.util.MathUtils;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class OrderDishItemViewModel extends ItemViewModel<ConfirmOrderViewModel> {

    private int dishServerId;

    private double dishPrice;

    private ObservableField<String> dishImageUrl = new ObservableField<>("http://aha.jpg");

    private ObservableField<String> dishName = new ObservableField<>("菜名");

    private ObservableField<Integer> reduceButtonVisibility = new ObservableField<>(View.VISIBLE);

    private ObservableField<String> orderDishNumber = new ObservableField<>("0");

    private ObservableField<Integer> orderDishNumberVisibility = new ObservableField<>(View.VISIBLE);

    private ObservableField<String> orderDishTotalPrice = new ObservableField<>("0.00");

    private BindingCommand reduceButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click reduce button
            Log.d(MyApplication.getTAG(), "click reduce button");
            orderDishNumber.set(String.valueOf(Integer.valueOf(orderDishNumber.get()) - 1));
            changeOrderDishNumber();
        }
    });

    private BindingCommand addButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click add button
            Log.d(MyApplication.getTAG(), "click add button");
            orderDishNumber.set(String.valueOf(Integer.valueOf(orderDishNumber.get()) + 1));
            changeOrderDishNumber();
        }
    });

    /**
     * 改变订单的菜的数量
     */
    public void changeOrderDishNumber() {
        orderDishTotalPrice.set(MathUtils.doubleKeepTwoToString(
                Integer.valueOf(orderDishNumber.get()) * dishPrice));
        int totalOrderDishNumber = 0;
        double orderTotalPrice = 0.0;
        for (OrderDish orderDish : viewModel.getOrder().getOrderDishes()) {
            if (orderDish.getDishServerId() == dishServerId) {
                orderDish.setDishNumber(Integer.valueOf(orderDishNumber.get()));
            }
            totalOrderDishNumber += orderDish.getDishNumber();
            orderTotalPrice += (orderDish.getDishNumber() * orderDish.getDish().getDishPrice());
        }
        viewModel.getOrder().setOrderDishNumber(totalOrderDishNumber);
        viewModel.getOrder().setOrderTotalPrice(orderTotalPrice);
        viewModel.getOrderDishNumberChangeEvent().setValue(this);
        Messenger.getDefault().send(new OrderDishDishesItemViewModel.MessengerHelper(dishServerId,
                orderDishNumber.get()), OrderDishDishesItemViewModel.TOKEN_ORDER_DISH_NUMBER_CHANGE);
    }

    public OrderDishItemViewModel(@NonNull ConfirmOrderViewModel confirmOrderViewModel,
                                  OrderDish orderDish) {
        super(confirmOrderViewModel);
        dishImageUrl.set(orderDish.getDish().getDishImageUrl());
        dishName.set(orderDish.getDish().getDishName());
        orderDishNumber.set(String.valueOf(orderDish.getDishNumber()));
        orderDishTotalPrice.set(MathUtils.doubleKeepTwoToString(orderDish.getDishNumber() * orderDish.getDish().getDishPrice()));
        dishServerId = orderDish.getDish().getServerId();
        dishPrice = orderDish.getDish().getDishPrice();
    }

    public ObservableField<String> getOrderDishTotalPrice() {
        return orderDishTotalPrice;
    }

    public BindingCommand getAddButtonClick() {
        return addButtonClick;
    }

    public ObservableField<Integer> getOrderDishNumberVisibility() {
        return orderDishNumberVisibility;
    }

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public BindingCommand getReduceButtonClick() {
        return reduceButtonClick;
    }

    public ObservableField<String> getDishImageUrl() {
        return dishImageUrl;
    }

    public ObservableField<String> getDishName() {
        return dishName;
    }

    public ObservableField<Integer> getReduceButtonVisibility() {
        return reduceButtonVisibility;
    }
}
