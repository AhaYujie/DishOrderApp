package com.aha.dishordersystem.ui.order_dish;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderDishViewModel extends BaseViewModel {

    public ObservableList<OrderDishCategoryItemViewModel> categoryItemViewModels
            = new ObservableArrayList<>();

    public ItemBinding<OrderDishCategoryItemViewModel> categoryItemBinding
            = ItemBinding.of(BR.categoryItemViewModel, R.layout.item_category);

    public ObservableList<OrderDishDishesItemViewModel> dishesItemViewModels
            = new ObservableArrayList<>();

    public ItemBinding<OrderDishDishesItemViewModel> dishesItemBinding
            = ItemBinding.of(BR.dishItemViewModel, R.layout.item_dish);

    private SingleLiveEvent<OrderDishCategoryItemViewModel> selectChangeObservable =
            new SingleLiveEvent<>();

    public OrderDishViewModel(@NonNull Application application) {
        super(application);
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                OrderDishCategoryItemViewModel categoryItemViewModel =
                        new OrderDishCategoryItemViewModel(this, "aha", true);
                categoryItemViewModels.add(categoryItemViewModel);
            }
            else {
                categoryItemViewModels.add(new OrderDishCategoryItemViewModel(this));
            }
        }
        for (int i = 0; i < 20; i++) {
            dishesItemViewModels.add(new OrderDishDishesItemViewModel(this));
        }
    }

    public SingleLiveEvent<OrderDishCategoryItemViewModel> getSelectChangeObservable() {
        return selectChangeObservable;
    }

    /**
     * 获取类别位置
     * @param orderDishCategoryItemViewModel
     * @return
     */
    public int getCategoryItemPosition(OrderDishCategoryItemViewModel orderDishCategoryItemViewModel) {
        return categoryItemViewModels.indexOf(orderDishCategoryItemViewModel);
    }

}
