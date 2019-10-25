package com.aha.dishordersystem.ui.order_dish;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class OrderDishDishesItemViewModel extends ItemViewModel<OrderDishViewModel> {

    public ObservableField<String> dishImageUrl;

    public ObservableField<String> dishName;

    public ObservableField<String> dishDetail;

    public ObservableField<String> dishPrice;

    public ObservableField<String> dishNumber;

    public ObservableField<Integer> reduceIconVisibility;

    public ObservableField<Integer> dishNumberVisibility;

    public OrderDishDishesItemViewModel(@NonNull OrderDishViewModel orderDishViewModel) {
        super(orderDishViewModel);
        dishImageUrl = new ObservableField<>("https://raw.githubusercontent.com/xilou31/Dishes-OrderingSystem/master/UI/%E7%82%B9%E9%A4%90%E7%95%8C%E9%9D%A21.jpg");
        dishName = new ObservableField<>("菜名");
        dishDetail = new ObservableField<>("菜简介");
        dishPrice = new ObservableField<>("4.7");
        dishNumber = new ObservableField<>("0");
        dishNumberVisibility = new ObservableField<>(View.INVISIBLE);
        reduceIconVisibility = new ObservableField<>(View.INVISIBLE);
    }

}
