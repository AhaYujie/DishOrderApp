package com.aha.dishordersystem.ui.order_dish;

import android.accounts.NetworkErrorException;
import android.app.Application;
import android.util.Log;
import android.view.View;

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
import com.aha.dishordersystem.data.network.json.DishCategoryJson;
import com.aha.dishordersystem.data.network.json.DishJson;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.ui.order_dish.confirm_order.ConfirmOrderFragment;
import com.aha.dishordersystem.util.JsonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderDishViewModel extends BaseViewModel<DataRepository> {

    private HistoryOrder order = new HistoryOrder();

    private ObservableField<Integer> floatingButtonVisibility = new ObservableField<>(View.INVISIBLE);

    private ObservableField<Integer> wrongPageVisibility = new ObservableField<>(View.GONE);

    private ObservableList<OrderDishCategoryItemViewModel> categoryItemViewModels
            = new ObservableArrayList<>();

    private ItemBinding<OrderDishCategoryItemViewModel> categoryItemBinding
            = ItemBinding.of(BR.categoryItemViewModel, R.layout.item_category);

    private ObservableList<OrderDishDishesItemViewModel> dishesItemViewModels
            = new ObservableArrayList<>();

    private ItemBinding<OrderDishDishesItemViewModel> dishesItemBinding
            = ItemBinding.of(BR.dishItemViewModel, R.layout.item_dish);

    private SingleLiveEvent<OrderDishCategoryItemViewModel> selectChangeObservable =
            new SingleLiveEvent<>();

    private SingleLiveEvent<OrderDishDishesItemViewModel> orderDishNumberChangeObservable =
            new SingleLiveEvent<>();

    private  SingleLiveEvent<Integer> setWrongPageVisibilityLiveEvent = new SingleLiveEvent<>();

    private BindingCommand wrongPageClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click wrong page reload
            setWrongPageVisibilityLiveEvent.setValue(View.GONE);
            initData();
        }
    });

    private BindingCommand floatingButtonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // click floating button
            Log.d(MyApplication.getTAG(), "click floating button");
            OrderDishViewModel.this.order.setOrderCreateTime(new Date());
            OrderDishViewModel.this.order.setOrderIsFinish(HistoryOrder.UN_FINISH);
            ConfirmOrderFragment.actionStart(OrderDishViewModel.this, OrderDishViewModel.this.order);
        }
    });

    public OrderDishViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        model.getAllDishes()
                .compose(RxUtils.schedulersTransformer())   // 线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)                        // 与ViewModel生命周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.getTAG(), "开始加载");
                        showDialog("正在加载...");
                    }
                })
                .subscribe(new Consumer<DishListJson>() {
                    @Override
                    public void accept(DishListJson dishListJson) throws Exception {
                        Log.d(MyApplication.getTAG(), "" + JsonUtils.dishListJsonToDishCategories(dishListJson));
                        if (dishListJson.getStatus() == DishListJson.STATUS_ERROR) {
                            throw new NetworkErrorException();
                        }

                        List<OrderDishDishesItemViewModel> allOrderDishViewModels = new ArrayList<>();

                        for (DishCategoryJson category : dishListJson.getDishCategories()) {

                            List<OrderDishDishesItemViewModel> dishList = new ArrayList<>();

                            for (DishJson dish : category.getDishes()) {
                                OrderDishDishesItemViewModel orderDishDishesItemViewModel =
                                        new OrderDishDishesItemViewModel(OrderDishViewModel.this,
                                                dish.getDishName(), dish.getDishImageUrl(),
                                                dish.getDishDetail(), String.valueOf(dish.getDishPrice()),
                                                "0", dish.getDishId());
                                dishList.add(orderDishDishesItemViewModel);
                            }

                            OrderDishCategoryItemViewModel orderDishCategoryItemViewModel = new
                                    OrderDishCategoryItemViewModel(OrderDishViewModel.this,
                                    category.getDishCategory(), false, dishList, "0");
                            categoryItemViewModels.add(orderDishCategoryItemViewModel);

                            allOrderDishViewModels.addAll(dishList);
                        }

                        OrderDishCategoryItemViewModel allCategory =
                                new OrderDishCategoryItemViewModel(OrderDishViewModel.this,
                                        "全部", true, allOrderDishViewModels, "0");
                        categoryItemViewModels.add(0, allCategory);
                        // 显示全部类别
                        dishesItemViewModels.addAll(allCategory.getDishesItemViewModelList());
                        // 存储数据到本地
                        model.saveDishToDB(dishListJson);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Log.d(MyApplication.getTAG(), "捕获异常: " + throwable.getMessage());
                        setWrongPageVisibilityLiveEvent.setValue(View.VISIBLE);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.getTAG(), "加载结束");
                        dismissDialog();
                    }
                });
    }

    /**
     * 获取类别位置
     * @param orderDishCategoryItemViewModel
     * @return
     */
    public int getCategoryItemPosition(OrderDishCategoryItemViewModel orderDishCategoryItemViewModel) {
        return categoryItemViewModels.indexOf(orderDishCategoryItemViewModel);
    }

    public BindingCommand getFloatingButtonClick() {
        return floatingButtonClick;
    }

    public HistoryOrder getOrder() {
        return order;
    }

    public SingleLiveEvent<OrderDishDishesItemViewModel> getOrderDishNumberChangeObservable() {
        return orderDishNumberChangeObservable;
    }

    public SingleLiveEvent<OrderDishCategoryItemViewModel> getSelectChangeObservable() {
        return selectChangeObservable;
    }

    public SingleLiveEvent<Integer> getSetWrongPageVisibilityLiveEvent() {
        return setWrongPageVisibilityLiveEvent;
    }

    public BindingCommand getWrongPageClick() {
        return wrongPageClick;
    }

    public ObservableField<Integer> getWrongPageVisibility() {
        return wrongPageVisibility;
    }

    public ObservableField<Integer> getFloatingButtonVisibility() {
        return floatingButtonVisibility;
    }

    public ObservableList<OrderDishCategoryItemViewModel> getCategoryItemViewModels() {
        return categoryItemViewModels;
    }

    public ItemBinding<OrderDishCategoryItemViewModel> getCategoryItemBinding() {
        return categoryItemBinding;
    }

    public ObservableList<OrderDishDishesItemViewModel> getDishesItemViewModels() {
        return dishesItemViewModels;
    }

    public ItemBinding<OrderDishDishesItemViewModel> getDishesItemBinding() {
        return dishesItemBinding;
    }

}
