package com.aha.dishordersystem.ui.history_order;

import android.app.Application;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HistoryOrderViewModel extends BaseViewModel<DataRepository> {

    private ObservableList<HistoryOrderItemViewModel> historyOrderItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<HistoryOrderItemViewModel> historyOrderItemViewModelItemBinding =
            ItemBinding.of(BR.historyOrderItemViewModel, R.layout.item_history_order);

    public HistoryOrderViewModel(Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    public ItemBinding<HistoryOrderItemViewModel> getHistoryOrderItemViewModelItemBinding() {
        return historyOrderItemViewModelItemBinding;
    }

    public ObservableList<HistoryOrderItemViewModel> getHistoryOrderItemViewModels() {
        return historyOrderItemViewModels;
    }
}
