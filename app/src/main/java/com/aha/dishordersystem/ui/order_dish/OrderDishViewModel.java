package com.aha.dishordersystem.ui.order_dish;

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
import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.model.dish.Dish;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.data.db.model.order.OrderDish;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.ui.dish_detail.DishDetailFragment;
import com.aha.dishordersystem.util.JsonUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderDishViewModel extends BaseViewModel<DataRepository> {

    private ObservableField<Integer> floatingButtonVisibility = new ObservableField<>(View.INVISIBLE);

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

//        DishCategory dishCategory = new DishCategory();
//        dishCategory.setCategoryName("aha");
//        List<OrderDish> orderDishes = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            OrderDish orderDish = new OrderDish();
//            orderDish.setDishNumber(1);
//            Dish dish = new Dish();
//            dish.setServerId(i);
//            dish.setDishImageUrl("http://aha.jpg");
//            dish.setDishName("aha");
//            dish.setDishPrice(4.7);
//            dish.setDishDetail("aha做的菜");
//            dish.setDishCategory(dishCategory);
//            orderDish.setDish(dish);
//            orderDish.setDishServerId(dish.getServerId());
//            orderDishes.add(orderDish);
//            dishCategory.getDishes().add(dish);
//        }
//        final HistoryOrder historyOrder = new HistoryOrder();
//        historyOrder.setOrderCreateTime(new Date());
//        historyOrder.setOrderIsFinish(1);
//        historyOrder.setOrderDishNumber(10);
//        historyOrder.getOrderDishes().addAll(orderDishes);
//
//        List<DishCategory> dishCategories = new ArrayList<>();
//        dishCategories.add(dishCategory);
//        model.saveDishToDB(dishCategories);
//        model.saveOrderToDB(historyOrder);
//
//        Log.d(MyApplication.getTAG(), "db: " + LitePal.findAll(OrderDish.class).size());
//        Log.d(MyApplication.getTAG(), "db: " + LitePal.findAll(HistoryOrder.class, true).size());

//        model.getAllOrders()
//                .compose(RxUtils.schedulersTransformer())
//                .doOnSubscribe(OrderDishViewModel.this)
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        Log.d(MyApplication.getTAG(), "正在加载");
//                        ToastUtils.showShort("正在加载");
//                    }
//                })
//                .subscribe(new Consumer<List<HistoryOrder>>() {
//
//                    @Override
//                    public void accept(List<HistoryOrder> historyOrders) throws Exception {
//                        Log.d(MyApplication.getTAG(), "accept: " + historyOrders);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        throwable.printStackTrace();
//                        Log.d(MyApplication.getTAG(), "error: " + throwable.getMessage());
//                    }
//                });
//        model.getAllDishes()
//                .compose(RxUtils.schedulersTransformer())
//                .doOnSubscribe(OrderDishViewModel.this)
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        Log.d(MyApplication.getTAG(), "正在加载");
//                        ToastUtils.showShort("正在加载");
//                    }
//                })
//                .subscribe(new Consumer<DishListJson>() {
//                    @Override
//                    public void accept(DishListJson dishListJson) throws Exception {
//                        List<DishCategory> dishCategories = JsonUtils.dishListJsonToDishCategories(dishListJson);
//                        Log.d(MyApplication.getTAG(), "accept: " + dishListJson.getStatus());
//                        Log.d(MyApplication.getTAG(), "accept: " + dishCategories);
//                        model.saveDishToDB(dishListJson);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.d(MyApplication.getTAG(), "error:" + throwable);
//                        throwable.printStackTrace();
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d(MyApplication.getTAG(), "finish");
//                    }
//                });



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
