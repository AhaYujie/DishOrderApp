package com.aha.dishordersystem.ui.history_order;

import android.app.Application;

import com.aha.dishordersystem.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class HistoryOrderViewModel extends BaseViewModel<DataRepository> {

    public HistoryOrderViewModel(Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

}
