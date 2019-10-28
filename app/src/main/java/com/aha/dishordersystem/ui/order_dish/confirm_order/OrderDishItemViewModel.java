package com.aha.dishordersystem.ui.order_dish.confirm_order;

import android.app.Application;

import androidx.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

public class OrderDishItemViewModel extends ItemViewModel<ConfirmOrderViewModel> {

    public OrderDishItemViewModel(@NonNull ConfirmOrderViewModel confirmOrderViewModel) {
        super(confirmOrderViewModel);
    }

}
