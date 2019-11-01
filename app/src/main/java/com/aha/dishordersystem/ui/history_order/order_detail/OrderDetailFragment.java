package com.aha.dishordersystem.ui.history_order.order_detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.databinding.FragmentConfirmOrderBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class OrderDetailFragment extends BaseFragment<FragmentConfirmOrderBinding, OrderDetailViewModel> {

    private HistoryOrder historyOrder;

    /**
     * 启动函数
     * @param viewModel
     * @param historyOrder
     */
    public static void actionStart(BaseViewModel viewModel, HistoryOrder historyOrder) {
        // 启动
        Bundle bundle = new Bundle();
        bundle.putParcelable("history_order", historyOrder);
        viewModel.startContainerActivity(OrderDetailFragment.class.getCanonicalName(), bundle);
    }

    /**
     * 初始化根布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_order_detail;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.orderDetailViewModel;
    }

    @Override
    public void initParam() {
        Bundle bundle = getArguments();
        historyOrder = bundle.getParcelable("history_order");
    }

    @Override
    public void initData() {
        viewModel.initData(historyOrder);
    }
}









