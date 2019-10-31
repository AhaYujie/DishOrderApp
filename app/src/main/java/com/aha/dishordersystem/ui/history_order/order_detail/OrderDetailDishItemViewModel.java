package com.aha.dishordersystem.ui.history_order.order_detail;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.util.MathUtils;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class OrderDetailDishItemViewModel extends ItemViewModel<OrderDetailViewModel> {

    private ObservableField<String> dishImageUrl = new ObservableField<>("http://aha.jpg");

    private ObservableField<String> dishName = new ObservableField<>("菜名");

    private ObservableField<String> orderDishNumber = new ObservableField<>("0");

    private ObservableField<String> orderDishPrice = new ObservableField<>("0.00");

    public OrderDetailDishItemViewModel(@NonNull OrderDetailViewModel orderDetailViewModel,
                                        OrderDish orderDish) {
        super(orderDetailViewModel);
        dishImageUrl.set(orderDish.getDish().getDishImageUrl());
        dishName.set(orderDish.getDish().getDishName());
        orderDishNumber.set(String.valueOf(orderDish.getDishNumber()));
        orderDishPrice.set(MathUtils.doubleKeepTwoToString(orderDish.getDishNumber() * orderDish.getDish().getDishPrice()));
    }

    public ObservableField<String> getOrderDishPrice() {
        return orderDishPrice;
    }

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public ObservableField<String> getDishName() {
        return dishName;
    }

    public ObservableField<String> getDishImageUrl() {
        return dishImageUrl;
    }
}
