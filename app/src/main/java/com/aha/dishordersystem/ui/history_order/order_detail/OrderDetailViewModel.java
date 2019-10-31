package com.aha.dishordersystem.ui.history_order.order_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.util.MathUtils;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderDetailViewModel extends BaseViewModel {

    private ObservableList<OrderDetailDishItemViewModel> dishItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<OrderDetailDishItemViewModel> dishItemViewModelItemBinding =
            ItemBinding.of(BR.orderDetailDishItemViewModel, R.layout.item_order_detail_dish);

    private ObservableField<String> orderCreateTime = new ObservableField<>("2000-04-07");

    private ObservableField<String> orderTotalPrice = new ObservableField<>("0.00");

    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 初始化数据
     * @param historyOrder
     */
    public void initData(HistoryOrder historyOrder) {
        for (OrderDish orderDish : historyOrder.getOrderDishes()) {
            dishItemViewModels.add(new OrderDetailDishItemViewModel(this, orderDish));
        }
        orderCreateTime.set(historyOrder.getOrderCreateTime().toLocaleString());
        orderTotalPrice.set(MathUtils.doubleKeepTwoToString(historyOrder.getOrderTotalPrice()));
    }

    public ObservableField<String> getOrderCreateTime() {
        return orderCreateTime;
    }

    public ObservableField<String> getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public ItemBinding<OrderDetailDishItemViewModel> getDishItemViewModelItemBinding() {
        return dishItemViewModelItemBinding;
    }

    public ObservableList<OrderDetailDishItemViewModel> getDishItemViewModels() {
        return dishItemViewModels;
    }
}

