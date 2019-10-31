package com.aha.dishordersystem.ui.order_dish;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.app.MyViewModelFactory;
import com.aha.dishordersystem.databinding.FragmentOrderDishBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import retrofit2.http.POST;


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
    public OrderDishViewModel initViewModel() {
        MyViewModelFactory myViewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, myViewModelFactory).get(OrderDishViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecyclerView = view.findViewById(R.id.order_dish_category_recyclerview);
    }

    @Override
    public void initData() {
        viewModel.initData();
    }

    @Override
    public void initViewObservable() {
        // 监听显示错误页面
        viewModel.getSetWrongPageVisibilityLiveEvent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.getWrongPageVisibility().set(integer);
            }
        });

        // 监听选择类别
        viewModel.getSelectChangeObservable().observe(this,
                new Observer<OrderDishCategoryItemViewModel>() {
                    @Override
                    public void onChanged(OrderDishCategoryItemViewModel orderDishCategoryItemViewModel) {
                        for (OrderDishCategoryItemViewModel orderDishCategoryItemViewModel1:
                                viewModel.getCategoryItemViewModels()) {
                            orderDishCategoryItemViewModel1.getCategoryTextColor().set(Color.GRAY);
                            orderDishCategoryItemViewModel1.getSelectLineFlagVisibility().set(View.INVISIBLE);
                        }
                        orderDishCategoryItemViewModel.getCategoryTextColor().set(getResources().getColor(R.color.colorPrimary));
                        orderDishCategoryItemViewModel.getSelectLineFlagVisibility().set(View.VISIBLE);
                        categoryRecyclerView.smoothScrollToPosition
                                (viewModel.getCategoryItemPosition(orderDishCategoryItemViewModel));
                    }
                });

        // 监听订单的菜数量的变化
        viewModel.getOrderDishNumberChangeObservable().observe(this,
                new Observer<OrderDishDishesItemViewModel>() {
                    @Override
                    public void onChanged(OrderDishDishesItemViewModel changeDishesItemViewModel) {
                        // 刷新类别菜数量和菜item的菜数量的UI
                        if (!"0".equals(changeDishesItemViewModel.getOrderDishNumber().get())) {
                            changeDishesItemViewModel.getDishNumberVisibility().set(View.VISIBLE);
                            changeDishesItemViewModel.getReduceIconVisibility().set(View.VISIBLE);
                        }
                        int totalOrderDishNumber = 0;   // 订单的菜的总数量

                        for (int categoryIndex = 1; categoryIndex < viewModel.getCategoryItemViewModels().size();
                             categoryIndex++) {

                            OrderDishCategoryItemViewModel categoryItemViewModel =
                                    viewModel.getCategoryItemViewModels().get(categoryIndex);

                            int categoryOrderDishNumber = 0;    // 订单的这个类别的菜的总数量

                            for (OrderDishDishesItemViewModel dishesItemViewModel :
                                    categoryItemViewModel.getDishesItemViewModelList()) {
                                String orderDishNumber = dishesItemViewModel.getOrderDishNumber().get();
                                if ("0".equals(orderDishNumber)) {
                                    dishesItemViewModel.getReduceIconVisibility().set(View.INVISIBLE);
                                    dishesItemViewModel.getDishNumberVisibility().set(View.INVISIBLE);
                                }
                                else {
                                    dishesItemViewModel.getReduceIconVisibility().set(View.VISIBLE);
                                    dishesItemViewModel.getDishNumberVisibility().set(View.VISIBLE);
                                }
                                categoryOrderDishNumber += Integer.valueOf(orderDishNumber);
                            }
                            categoryItemViewModel.getOrderDishNumber().
                                    set(String.valueOf(categoryOrderDishNumber));
                            if ("0".equals(categoryItemViewModel.getOrderDishNumber().get())) {
                                categoryItemViewModel.getOrderDishNumberVisibility().set(View.INVISIBLE);
                            }
                            else {
                                categoryItemViewModel.getOrderDishNumberVisibility().set(View.VISIBLE);
                            }

                            totalOrderDishNumber += categoryOrderDishNumber;
                        }
                        // 更新(全部)类别的U和悬浮按钮UI
                        OrderDishCategoryItemViewModel allCategoryItemViewModel =
                                viewModel.getCategoryItemViewModels().get(0);
                        allCategoryItemViewModel.getOrderDishNumber().set(String.valueOf(totalOrderDishNumber));
                        if ("0".equals(allCategoryItemViewModel.getOrderDishNumber().get())) {
                            allCategoryItemViewModel.getOrderDishNumberVisibility().set(View.INVISIBLE);
                            viewModel.getFloatingButtonVisibility().set(View.INVISIBLE);
                        }
                        else {
                            allCategoryItemViewModel.getOrderDishNumberVisibility().set(View.VISIBLE);
                            viewModel.getFloatingButtonVisibility().set(View.VISIBLE);
                        }
                    }
                });
    }
}













