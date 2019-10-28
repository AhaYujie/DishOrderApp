package com.aha.dishordersystem.ui.order_dish.confirm_order;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ConfirmOrderViewModel extends BaseViewModel {

    private ObservableList<OrderDishItemViewModel> orderDishItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<OrderDishItemViewModel> orderDishItemViewModelItemBinding =
            ItemBinding.of(BR.orderDishItemViewModel, R.layout.item_order_dish);

    private ObservableField<String> orderDishTotalPrice = new ObservableField<>("0.00");

    private PayButton payButton = new PayButton();

    private BindingCommand backButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 退出这个界面
            finish();
        }
    });

    public ConfirmOrderViewModel(@NonNull Application application) {
        super(application);
        for (int i = 0; i < 20; i++) {
            orderDishItemViewModels.add(new OrderDishItemViewModel(this));
        }
    }

    /**
     * 初始化数据
     * @param order
     */
    public void initData(HistoryOrder order) {
        // TODO:init data
    }

    public class PayButton {
        private ObservableField<String> payButtonText = new ObservableField<>("支付订单");
        private ObservableField<Boolean> payButtonClickable = new ObservableField<>(Boolean.TRUE);
        private ObservableField<Drawable> payButtonColor = new ObservableField<>
                (getApplication().getResources().getDrawable(R.drawable.button_green));
        private BindingCommand payButtonClick = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                // TODO:click pay button
                Log.d(MyApplication.getTAG(), "click pay button");
            }
        });

        /**
         * 设置支付按钮可不可以点击
         * @param payable true可以点击，false不可以点击
         */
        public void setPayable(Boolean payable) {
            if (payable) {
                payButtonText.set("支付订单");
                payButtonColor.set(getApplication().getResources().getDrawable(R.drawable.button_green));
                payButtonClickable.set(Boolean.TRUE);
            }
            else {
                payButtonText.set("尚未点菜");
                payButtonColor.set(getApplication().getResources().getDrawable(R.drawable.button_grey));
                payButtonClickable.set(Boolean.FALSE);
            }
        }

        public ObservableField<String> getPayButtonText() {
            return payButtonText;
        }

        public ObservableField<Boolean> getPayButtonClickable() {
            return payButtonClickable;
        }

        public ObservableField<Drawable> getPayButtonColor() {
            return payButtonColor;
        }

        public BindingCommand getPayButtonClick() {
            return payButtonClick;
        }
    }

    public PayButton getPayButton() {
        return payButton;
    }

    public ObservableField<String> getOrderDishTotalPrice() {
        return orderDishTotalPrice;
    }

    public BindingCommand getBackButtonClick() {
        return backButtonClick;
    }

    public ItemBinding<OrderDishItemViewModel> getOrderDishItemViewModelItemBinding() {
        return orderDishItemViewModelItemBinding;
    }

    public ObservableList<OrderDishItemViewModel> getOrderDishItemViewModels() {
        return orderDishItemViewModels;
    }
}
