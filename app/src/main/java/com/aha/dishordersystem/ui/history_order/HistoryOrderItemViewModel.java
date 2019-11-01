package com.aha.dishordersystem.ui.history_order;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.dish.Dish;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.ui.history_order.order_detail.OrderDetailFragment;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class HistoryOrderItemViewModel extends ItemViewModel<HistoryOrderViewModel> {

    private HistoryOrder historyOrder;

    private ObservableField<String> firstDishImageUrl = new ObservableField<>("http://aha.jpg");

    private ObservableField<String> firstDishName = new ObservableField<>("第一个菜名");

    private ObservableField<String> orderDishNumber = new ObservableField<>();

    private ObservableField<String> orderCreateTime = new ObservableField<>();

    private ObservableField<String> orderFinishFlag = new ObservableField<>();

    private BindingCommand historyOrderClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click history order item
            Log.d(MyApplication.getTAG(), "click history order item");
            OrderDetailFragment.actionStart(viewModel, historyOrder);
        }
    });

    private BindingCommand deleteOrderClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click delete order button
            Log.d(MyApplication.getTAG(), "click delete order button");
            // viewModel.deleteOrder(HistoryOrderItemViewModel.this);
            viewModel.getClickDeleteOrderButton().setValue(HistoryOrderItemViewModel.this);
        }
    });

    public HistoryOrderItemViewModel(@NonNull HistoryOrderViewModel historyOrderViewModel,
                                     HistoryOrder historyOrder) {
        super(historyOrderViewModel);
        this.historyOrder = historyOrder;
        OrderDish firstOrderDish = historyOrder.getOrderDishes().get(0);
        firstDishImageUrl.set(firstOrderDish.getDish().getDishImageUrl());
        firstDishName.set(firstOrderDish.getDish().getDishName());
        orderDishNumber.set(String.valueOf(historyOrder.getOrderDishNumber()));
        orderCreateTime.set(historyOrder.getOrderCreateTime().toLocaleString());
        if (historyOrder.getOrderIsFinish() == HistoryOrder.FINISHED) {
            orderFinishFlag.set("已完成");
        }
        else {
            orderFinishFlag.set("未完成");
        }
    }

    public HistoryOrder getHistoryOrder() {
        return historyOrder;
    }

    public BindingCommand getDeleteOrderClick() {
        return deleteOrderClick;
    }

    public ObservableField<String> getOrderFinishFlag() {
        return orderFinishFlag;
    }

    public ObservableField<String> getOrderCreateTime() {
        return orderCreateTime;
    }

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public ObservableField<String> getFirstDishName() {
        return firstDishName;
    }

    public ObservableField<String> getFirstDishImageUrl() {
        return firstDishImageUrl;
    }

    public BindingCommand getHistoryOrderClick() {
        return historyOrderClick;
    }
}
