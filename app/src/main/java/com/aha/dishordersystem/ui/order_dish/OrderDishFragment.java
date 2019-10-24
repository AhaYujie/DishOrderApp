package com.aha.dishordersystem.ui.order_dish;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.databinding.FragmentOrderDishBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


public class OrderDishFragment extends BaseFragment<FragmentOrderDishBinding, OrderDishViewModel> {

    private RecyclerView categoryRecyclerView;

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
        return R.layout.fragment_order_dish;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.orderDishViewModel;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecyclerView = view.findViewById(R.id.order_dish_category_recyclerview);
    }

    @Override
    public void initViewObservable() {
        // 监听选择类别
        viewModel.getSelectChangeObservable().observe(this, new Observer<OrderDishCategoryItemViewModel>() {
            @Override
            public void onChanged(OrderDishCategoryItemViewModel orderDishCategoryItemViewModel) {
                for (OrderDishCategoryItemViewModel orderDishCategoryItemViewModel1:
                    viewModel.categoryItemViewModels) {
                    orderDishCategoryItemViewModel1.categoryTextColor.set(Color.GRAY);
                    orderDishCategoryItemViewModel1.selectLineFlagVisibility.set(View.INVISIBLE);
                }
                orderDishCategoryItemViewModel.categoryTextColor.set(getResources().getColor(R.color.colorPrimary));
                orderDishCategoryItemViewModel.selectLineFlagVisibility.set(View.VISIBLE);
                categoryRecyclerView.smoothScrollToPosition
                        (viewModel.getCategoryItemPosition(orderDishCategoryItemViewModel));
            }
        });
    }
}













