package com.aha.dishordersystem.ui.history_order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyViewModelFactory;
import com.aha.dishordersystem.databinding.FragmentHistroyOrderBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


public class HistoryOrderFragment extends BaseFragment<FragmentHistroyOrderBinding, HistoryOrderViewModel> {

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
        return R.layout.fragment_histroy_order;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.historyOrderViewModel;
    }

    @Override
    public HistoryOrderViewModel initViewModel() {
        MyViewModelFactory myViewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, myViewModelFactory).get(HistoryOrderViewModel.class);
    }

}