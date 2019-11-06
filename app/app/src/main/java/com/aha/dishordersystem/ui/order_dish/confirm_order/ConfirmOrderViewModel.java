package com.aha.dishordersystem.ui.order_dish.confirm_order;

import android.accounts.NetworkErrorException;
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
import com.aha.dishordersystem.data.DataRepository;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.data.network.json.OrderDishesJson;
import com.aha.dishordersystem.data.network.json.PayOrderResponseJson;
import com.aha.dishordersystem.ui.history_order.HistoryOrderViewModel;
import com.aha.dishordersystem.ui.history_order.order_detail.OrderDetailFragment;
import com.aha.dishordersystem.util.JsonUtils;
import com.aha.dishordersystem.util.MathUtils;
import com.aha.dishordersystem.util.StringUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ConfirmOrderViewModel extends BaseViewModel<DataRepository> {

    private HistoryOrder order;

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

    private SingleLiveEvent<OrderDishItemViewModel> orderDishNumberChangeEvent =
            new SingleLiveEvent<>();

    private BindingCommand clearOrderDishListClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 清空订单
            clearOrderList();
        }
    });

    /**
     * 清空订单
     */
    private void clearOrderList() {
        for (OrderDishItemViewModel orderDishItemViewModel : orderDishItemViewModels) {
            orderDishItemViewModel.getOrderDishNumber().set("0");
            orderDishItemViewModel.changeOrderDishNumber();
        }
    }

    public ConfirmOrderViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    /**
     * 初始化数据
     * @param order
     */
    public void initData(HistoryOrder order) {
        this.order = order;
        if (order.getOrderDishNumber() != 0) {
            orderDishTotalPrice.set(MathUtils.doubleKeepTwoToString(order.getOrderTotalPrice()));
            payButton.setPayable(true);
        }
        for (OrderDish orderDish : order.getOrderDishes()) {
            orderDishItemViewModels.add(new OrderDishItemViewModel(this, orderDish));
        }
    }

    public class PayButton {
        private ObservableField<String> payButtonText = new ObservableField<>("支付订单");
        private ObservableField<Boolean> payButtonClickable = new ObservableField<>(Boolean.TRUE);
        private ObservableField<Drawable> payButtonColor = new ObservableField<>
                (getApplication().getResources().getDrawable(R.drawable.button_green));
        private BindingCommand payButtonClick = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                Log.d(MyApplication.getTAG(), "click pay button");
                // 请求服务器
                OrderDishesJson orderDishesJson = JsonUtils.historyOrderToOrderDishesJson(
                        ConfirmOrderViewModel.this.order
                );
                ConfirmOrderViewModel.this.model.payOrder(orderDishesJson)
                        .compose(RxUtils.schedulersTransformer())
                        .compose(RxUtils.exceptionTransformer())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                Log.d(MyApplication.getTAG(), "请求服务器支付订单");
                                showDialog("正在支付...");
                            }
                        })
                        .subscribe(new Consumer<PayOrderResponseJson>() {
                            @Override
                            public void accept(PayOrderResponseJson payOrderResponseJson) throws Exception {
                                if (payOrderResponseJson.getStatus() == PayOrderResponseJson.PAY_ORDER_ERROR) {
                                    throw new NetworkErrorException();
                                }
                                Log.d(MyApplication.getTAG(), "accept response: " + payOrderResponseJson.getStatus());
                                order.setSerialNumber(StringUtils.getRandomString());
                                // 保存订单到本地数据库
                                order.setOrderIsFinish(HistoryOrder.FINISHED);
                                model.saveOrderToDB((HistoryOrder) order.clone());
                                // 通知订单列表变化
                                Messenger.getDefault().send(order.clone(),
                                        HistoryOrderViewModel.TOKEN_HISTORY_ORDER_NUMBER_CHANGE);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // 支付失败
                                dismissDialog();
                                ToastUtils.showShort("支付失败, 网络出问题了...");
                                Log.d(MyApplication.getTAG(), "异常: " + throwable.getMessage());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                // 支付完成
                                Log.d(MyApplication.getTAG(), "支付完成");
                                dismissDialog();
                                ToastUtils.showShort("支付成功");
                                // 跳转到订单详情界面并结束这个确认订单界面
                                OrderDetailFragment.actionStart(ConfirmOrderViewModel.this,
                                        (HistoryOrder) order.clone());
                                // 清空订单列表
                                clearOrderList();
                                ConfirmOrderViewModel.this.finish();
                            }
                        });
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

    public BindingCommand getClearOrderDishListClick() {
        return clearOrderDishListClick;
    }

    public HistoryOrder getOrder() {
        return order;
    }

    public SingleLiveEvent<OrderDishItemViewModel> getOrderDishNumberChangeEvent() {
        return orderDishNumberChangeEvent;
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
