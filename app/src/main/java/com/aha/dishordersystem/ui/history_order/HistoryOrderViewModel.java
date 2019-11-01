package com.aha.dishordersystem.ui.history_order;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.DataRepository;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HistoryOrderViewModel extends BaseViewModel<DataRepository> {

    public static final String TOKEN_HISTORY_ORDER_NUMBER_CHANGE = "token_history_order_number_change";

    private ObservableField<Integer> noOrderHint = new ObservableField<>(View.GONE);

    private ObservableList<HistoryOrderItemViewModel> historyOrderItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<HistoryOrderItemViewModel> historyOrderItemViewModelItemBinding =
            ItemBinding.of(BR.historyOrderItemViewModel, R.layout.item_history_order);

    private SingleLiveEvent<HistoryOrderItemViewModel> clickDeleteOrderButton =
            new SingleLiveEvent<>();

    public HistoryOrderViewModel(Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    /**
     * 删除订单
     * @param historyOrderItemViewModel
     */
    public void deleteOrder(HistoryOrderItemViewModel historyOrderItemViewModel) {
        historyOrderItemViewModels.remove(historyOrderItemViewModel);
        if (historyOrderItemViewModels.isEmpty()) {
            noOrderHint.set(View.VISIBLE);
        }
        model.deleteOrder(historyOrderItemViewModel.getHistoryOrder());
    }

    /**
     * 初始化数据，获取全部订单
     */
    public void initData() {
        Messenger.getDefault().register(this, TOKEN_HISTORY_ORDER_NUMBER_CHANGE,
                HistoryOrder.class, new BindingConsumer<HistoryOrder>() {
                    @Override
                    public void call(HistoryOrder historyOrder) {
                        historyOrderItemViewModels.add(0, new HistoryOrderItemViewModel(
                                HistoryOrderViewModel.this, historyOrder));
                        if (!historyOrderItemViewModels.isEmpty()) {
                            noOrderHint.set(View.GONE);
                        }
                    }
                });
        model.getAllOrders()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.getTAG(), "开始获取订单");
                        showDialog("正在加载...");
                    }
                })
                .subscribe(new Consumer<List<HistoryOrder>>() {
                    @Override
                    public void accept(List<HistoryOrder> historyOrders) throws Exception {
                        Log.d(MyApplication.getTAG(), "获取 response");
                        for (HistoryOrder historyOrder : historyOrders) {
                            historyOrderItemViewModels.add(new HistoryOrderItemViewModel(
                                    HistoryOrderViewModel.this, historyOrder
                            ));
                        }
                        if (historyOrders.isEmpty()) {
                            noOrderHint.set(View.VISIBLE);
                        }
                        else {
                            noOrderHint.set(View.GONE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.getTAG(), "捕获异常: " + throwable.getMessage());
                        dismissDialog();
                        ToastUtils.showShort("加载出问题了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.getTAG(), "加载完成");
                        dismissDialog();
                    }
                });
    }

    public SingleLiveEvent<HistoryOrderItemViewModel> getClickDeleteOrderButton() {
        return clickDeleteOrderButton;
    }

    public ObservableField<Integer> getNoOrderHint() {
        return noOrderHint;
    }

    public ItemBinding<HistoryOrderItemViewModel> getHistoryOrderItemViewModelItemBinding() {
        return historyOrderItemViewModelItemBinding;
    }

    public ObservableList<HistoryOrderItemViewModel> getHistoryOrderItemViewModels() {
        return historyOrderItemViewModels;
    }


}
