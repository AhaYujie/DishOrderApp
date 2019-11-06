package com.aha.dishordersystem.ui.order_dish;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.dish.Dish;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.ui.order_dish.dish_detail.DishDetailFragment;
import com.aha.dishordersystem.ui.order_dish.dish_detail.DishDetailViewModel;
import com.aha.dishordersystem.util.MathUtils;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;

public class OrderDishDishesItemViewModel extends ItemViewModel<OrderDishViewModel> {

    public static final String TOKEN_ORDER_DISH_NUMBER_CHANGE = "token_order_dish_number_change";

    private int dishServerId;

    private ObservableField<String> dishImageUrl;

    private ObservableField<String> dishName;

    private ObservableField<String> dishDetail;

    private ObservableField<String> dishPrice;

    private ObservableField<String> orderDishNumber;

    private ObservableField<Integer> reduceIconVisibility;

    private ObservableField<Integer> dishNumberVisibility;

    private BindingCommand dishItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click dish item
            Log.d(MyApplication.getTAG(), "click dish item");
            DishDetailFragment.actionStart(viewModel, dishName.get(), dishImageUrl.get(),
                    dishPrice.get(), dishDetail.get(), orderDishNumber.get(), dishServerId);
        }
    });

    private BindingCommand reduceButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click reduce button
            Log.d(MyApplication.getTAG(), "click reduce button");
            changeOrderDishNumber(String.valueOf(Integer.valueOf(orderDishNumber.get()) - 1));
        }
    });

    private BindingCommand addButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click add button
            Log.d(MyApplication.getTAG(), "click add button");
            changeOrderDishNumber(String.valueOf(Integer.valueOf(orderDishNumber.get()) + 1));
        }
    });

    private void changeOrderDishNumber(String number) {
        orderDishNumber.set(number);
        OrderDish newOrderDish = null;
        for (OrderDish orderDish : viewModel.getOrder().getOrderDishes()) {
            if (orderDish.getDishServerId() == dishServerId) {
                newOrderDish = orderDish;
                break;
            }
        }
        if (newOrderDish == null) {
            newOrderDish = new OrderDish();
            newOrderDish.setDishServerId(dishServerId);
            Dish dish = new Dish();
            dish.setServerId(dishServerId);
            dish.setDishName(dishName.get());
            dish.setDishDetail(dishDetail.get());
            dish.setDishPrice(Double.valueOf(dishPrice.get()));
            dish.setDishImageUrl(dishImageUrl.get());
            newOrderDish.setDish(dish);
            viewModel.getOrder().getOrderDishes().add(newOrderDish);
        }
        newOrderDish.setDishNumber(Integer.valueOf(orderDishNumber.get()));
        if (newOrderDish.getDishNumber() == 0) {
            viewModel.getOrder().getOrderDishes().remove(newOrderDish);
        }
        int orderDishNumber = 0;
        double orderDishTotalPrice = 0.00;
        for (OrderDish orderDish : viewModel.getOrder().getOrderDishes()) {
            orderDishNumber += orderDish.getDishNumber();
            orderDishTotalPrice += (orderDish.getDishNumber() * orderDish.getDish().getDishPrice());
        }
        viewModel.getOrder().setOrderDishNumber(orderDishNumber);
        viewModel.getOrder().setOrderTotalPrice(orderDishTotalPrice);
        Log.d(MyApplication.getTAG(), "order change: " + viewModel.getOrder());
        viewModel.getOrderDishNumberChangeObservable().setValue(OrderDishDishesItemViewModel.this);
    }

    public OrderDishDishesItemViewModel(@NonNull OrderDishViewModel orderDishViewModel) {
        super(orderDishViewModel);
        dishImageUrl = new ObservableField<>();
        dishName = new ObservableField<>("菜名");
        dishDetail = new ObservableField<>("菜简介");
        dishPrice = new ObservableField<>("0.00");
        orderDishNumber = new ObservableField<>("0");
        dishNumberVisibility = new ObservableField<>(View.INVISIBLE);
        reduceIconVisibility = new ObservableField<>(View.INVISIBLE);
    }

    public OrderDishDishesItemViewModel(@NonNull OrderDishViewModel orderDishViewModel,
                                        String dishName, String dishImageUrl, String dishDetail,
                                        String dishPrice, String orderDishNumber, int dishServerId) {
        this(orderDishViewModel);
        this.dishName.set(dishName);
        this.dishImageUrl.set(dishImageUrl);
        this.dishDetail.set(dishDetail);
        this.dishPrice.set(MathUtils.doubleKeepTwoToString(dishPrice));
        if (!"0".equals(orderDishNumber)) {
            this.orderDishNumber.set(orderDishNumber);
            dishNumberVisibility.set(View.VISIBLE);
            dishNumberVisibility.set(View.VISIBLE);
        }
        this.dishServerId = dishServerId;
        Messenger.getDefault().register(this, TOKEN_ORDER_DISH_NUMBER_CHANGE,
                MessengerHelper.class, new BindingConsumer<MessengerHelper>() {
            @Override
            public void call(MessengerHelper messengerHelper) {
                if (messengerHelper.dishServerId == OrderDishDishesItemViewModel.this.dishServerId) {
                    Log.d(MyApplication.getTAG(), "call");
                    changeOrderDishNumber(messengerHelper.orderNumber);
                }
            }
        });
    }

    public static class MessengerHelper {
        public int dishServerId;
        public String orderNumber;
        public MessengerHelper(int dishServerId, String orderNumber) {
            this.dishServerId = dishServerId;
            this.orderNumber = orderNumber;
        }
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

    public ObservableField<String> getOrderDishNumber() {
        return orderDishNumber;
    }

    public ObservableField<Integer> getReduceIconVisibility() {
        return reduceIconVisibility;
    }

    public ObservableField<Integer> getDishNumberVisibility() {
        return dishNumberVisibility;
    }
}
