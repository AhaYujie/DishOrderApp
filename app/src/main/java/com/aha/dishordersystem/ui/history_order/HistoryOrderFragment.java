package com.aha.dishordersystem.ui.history_order;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.app.MyViewModelFactory;
import com.aha.dishordersystem.databinding.FragmentHistroyOrderBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


public class HistoryOrderFragment extends BaseFragment<FragmentHistroyOrderBinding, HistoryOrderViewModel> {

    private AlertDialog.Builder alertDialog;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("提示");
        alertDialog.setMessage("你确定要删除订单吗?");
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
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

    @Override
    public void initData() {
        viewModel.initData();
    }

    @Override
    public void initViewObservable() {
        // 监听点击删除订单按钮
        viewModel.getClickDeleteOrderButton().observe(this, new Observer<HistoryOrderItemViewModel>() {
            @Override
            public void onChanged(final HistoryOrderItemViewModel historyOrderItemViewModel) {
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 确认删除订单
                        viewModel.deleteOrder(historyOrderItemViewModel);
                    }
                });
                alertDialog.show();
            }
        });
    }

    public AlertDialog.Builder getAlertDialog() {
        return alertDialog;
    }
}