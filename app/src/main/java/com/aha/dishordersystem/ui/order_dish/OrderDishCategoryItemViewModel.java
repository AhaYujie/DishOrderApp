package com.aha.dishordersystem.ui.order_dish;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class OrderDishCategoryItemViewModel extends ItemViewModel<OrderDishViewModel> {

    private ObservableField<String> categoryName;

    private ObservableField<Integer> categoryTextColor;

    private ObservableField<Integer> selectLineFlagVisibility;

    private ObservableField<String> orderDishNumber;

    private ObservableField<Integer> orderDishNumberVisibility;

    public BindingCommand categoryItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isSelect()) {
                // TODO:切换类别
                viewModel.getSelectChangeObservable().setValue(OrderDishCategoryItemViewModel.this);
            }
        }
    });

    public OrderDishCategoryItemViewModel(@NonNull OrderDishViewModel orderDishViewModel) {
        super(orderDishViewModel);
        categoryName = new ObservableField<>("其他");
        categoryTextColor = new ObservableField<>(Color.GRAY);
        selectLineFlagVisibility = new ObservableField<>(View.INVISIBLE);
        orderDishNumber = new ObservableField<>("0");
        orderDishNumberVisibility = new ObservableField<>(View.INVISIBLE);
    }

    /**
     *
     * @param orderDishViewModel
     * @param categoryName
     * @param selectLineFlagVisibility true则选择类别，否则false
     */
    public OrderDishCategoryItemViewModel(@NonNull OrderDishViewModel orderDishViewModel,
                                          String categoryName, boolean selectLineFlagVisibility) {
        this(orderDishViewModel);
        this.categoryName.set(categoryName);
        if (selectLineFlagVisibility) {
            viewModel.getSelectChangeObservable().setValue(this);
        }
    }

    /**
     * 类别是否被选择
     * @return
     */
    private boolean isSelect() {
        return Integer.valueOf(View.VISIBLE).equals(selectLineFlagVisibility.get());
    }



    public ObservableField<String> getCategoryName() {
        return categoryName;
    }

    public ObservableField<Integer> getCategoryTextColor() {
        return categoryTextColor;
    }

    public ObservableField<Integer> getSelectLineFlagVisibility() {
        return selectLineFlagVisibility;
    }

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public ObservableField<Integer> getOrderDishNumberVisibility() {
        return orderDishNumberVisibility;
    }

    public BindingCommand getCategoryItemClick() {
        return categoryItemClick;
    }
}
