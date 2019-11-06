package com.aha.dishordersystem.app;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.aha.dishordersystem.data.DataRepository;
import com.aha.dishordersystem.ui.history_order.HistoryOrderViewModel;
import com.aha.dishordersystem.ui.history_order.order_detail.OrderDetailViewModel;
import com.aha.dishordersystem.ui.order_dish.OrderDishViewModel;
import com.aha.dishordersystem.ui.order_dish.confirm_order.ConfirmOrderViewModel;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile MyViewModelFactory INSTANCE;

    private final Application application;

    private final DataRepository dataRepository;

    public static MyViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (MyViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyViewModelFactory(application, Injection.provideDataRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private MyViewModelFactory(Application application, DataRepository dataRepository) {
        this.application = application;
        this.dataRepository = dataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OrderDishViewModel.class)) {
            return (T) new OrderDishViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(HistoryOrderViewModel.class)) {
            return (T) new HistoryOrderViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(ConfirmOrderViewModel.class)) {
            return (T) new ConfirmOrderViewModel(application, dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
