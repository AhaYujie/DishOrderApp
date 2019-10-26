package com.aha.dishordersystem.ui.order_dish;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.app.MyApplication;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class OrderDishDishesItemViewModel extends ItemViewModel<OrderDishViewModel> {

    private ObservableField<String> dishImageUrl;

    private ObservableField<String> dishName;

    private ObservableField<String> dishDetail;

    private ObservableField<String> dishPrice;

    private ObservableField<String> dishNumber;

    private ObservableField<Integer> reduceIconVisibility;

    private ObservableField<Integer> dishNumberVisibility;

    private BindingCommand dishItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:click dish item
            Log.d(MyApplication.getTAG(), "click dish item");
        }
    });

    private BindingCommand reduceButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:click reduce button
            Log.d(MyApplication.getTAG(), "click reduce button");
        }
    });

    private BindingCommand addButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:click add button
            Log.d(MyApplication.getTAG(), "click add button");
        }
    });

    public OrderDishDishesItemViewModel(@NonNull OrderDishViewModel orderDishViewModel) {
        super(orderDishViewModel);
        dishImageUrl = new ObservableField<>("https://raw.githubusercontent.com/xilou31/Dishes-OrderingSystem/master/UI/%E7%82%B9%E9%A4%90%E7%95%8C%E9%9D%A21.jpg");
        dishName = new ObservableField<>("菜名");
        dishDetail = new ObservableField<>("菜简介");
        dishPrice = new ObservableField<>("0.00");
        dishNumber = new ObservableField<>("0");
        dishNumberVisibility = new ObservableField<>(View.INVISIBLE);
        reduceIconVisibility = new ObservableField<>(View.INVISIBLE);
    }

    public BindingCommand getReduceButtonClick() {
        return reduceButtonClick;
    }

    public BindingCommand getAddButtonClick() {
        return addButtonClick;
    }

    public BindingCommand getDishItemClick() {
        return dishItemClick;
    }

    public ObservableField<String> getDishImageUrl() {
        return dishImageUrl;
    }

    public ObservableField<String> getDishName() {
        return dishName;
    }

    public ObservableField<String> getDishDetail() {
        return dishDetail;
    }

    public ObservableField<String> getDishPrice() {
        return dishPrice;
    }

    public ObservableField<String> getDishNumber() {
        return dishNumber;
    }

    public ObservableField<Integer> getReduceIconVisibility() {
        return reduceIconVisibility;
    }

    public ObservableField<Integer> getDishNumberVisibility() {
        return dishNumberVisibility;
    }
}
