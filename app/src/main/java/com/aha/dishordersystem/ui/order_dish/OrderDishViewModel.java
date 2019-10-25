package com.aha.dishordersystem.ui.order_dish;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.DataRepository;
import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.model.dish.Dish;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderDishViewModel extends BaseViewModel<DataRepository> {

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

    public OrderDishViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
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
