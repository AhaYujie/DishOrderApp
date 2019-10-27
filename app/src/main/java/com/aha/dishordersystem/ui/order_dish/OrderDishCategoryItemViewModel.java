package com.aha.dishordersystem.ui.order_dish;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.R;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;

public class OrderDishCategoryItemViewModel extends ItemViewModel<OrderDishViewModel> {

    private List<OrderDishDishesItemViewModel> dishesItemViewModelList = new ArrayList<>();

    private ObservableField<String> categoryName;

    private ObservableField<Integer> categoryTextColor;

    private ObservableField<Integer> selectLineFlagVisibility;

    private ObservableField<String> orderDishNumber;

    private ObservableField<Integer> orderDishNumberVisibility;

    public BindingCommand categoryItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isSelect()) {
                // 切换类别
                viewModel.getDishesItemViewModels().clear();
                viewModel.getDishesItemViewModels().addAll(dishesItemViewModelList);
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
     * @param dishesItemViewModelList
     */
    public OrderDishCategoryItemViewModel(@NonNull OrderDishViewModel orderDishViewModel,
                                          String categoryName, boolean selectLineFlagVisibility,
                                          List<OrderDishDishesItemViewModel> dishesItemViewModelList,
                                          String orderDishNumber) {
        this(orderDishViewModel);
        this.categoryName.set(categoryName);
        if (selectLineFlagVisibility) {
            this.selectLineFlagVisibility.set(View.VISIBLE);
            categoryTextColor.set(viewModel.getApplication().getResources().getColor(R.color.colorPrimary));
        }
        this.dishesItemViewModelList = dishesItemViewModelList;
        if (!"0".equals(orderDishNumber)) {
            this.orderDishNumber.set(orderDishNumber);
            orderDishNumberVisibility.set(View.VISIBLE);
        }
    }

    /**
     * 类别是否被选择
     * @return
     */
    private boolean isSelect() {
        return Integer.valueOf(View.VISIBLE).equals(selectLineFlagVisibility.get());
    }

    public List<OrderDishDishesItemViewModel> getDishesItemViewModelList() {
        return dishesItemViewModelList;
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
