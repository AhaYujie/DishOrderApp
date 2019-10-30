package com.aha.dishordersystem.ui.history_order.order_detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.databinding.FragmentConfirmOrderBinding;

import me.goldze.mvvmhabit.base.BaseFragment;

public class OrderDetailkFragment extends BaseFragment<FragmentConfirmOrderBinding, OrderDetailViewModel> {

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
}
