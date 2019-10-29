package com.aha.dishordersystem.ui.order_dish.confirm_order;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.data.db.model.order.HistoryOrder;
import com.aha.dishordersystem.databinding.FragmentConfirmOrderBinding;
import com.aha.dishordersystem.util.MathUtils;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class ConfirmOrderFragment extends BaseFragment<FragmentConfirmOrderBinding,
        ConfirmOrderViewModel> {

    private HistoryOrder order;

    /**
     * 启动函数
     * @param viewModel
     * @param order
     */
    public static void actionStart(BaseViewModel viewModel, HistoryOrder order) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", order);
        viewModel.startContainerActivity(ConfirmOrderFragment.class.getCanonicalName(), bundle);
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
        return R.layout.fragment_confirm_order;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.confirmOrderViewModel;
    }

    @Override
    public void initParam() {
        Bundle bundle = getArguments();
        order = bundle.getParcelable("order");
    }

    @Override
    public void initData() {
        viewModel.initData(order);
    }

    @Override
    public void initViewObservable() {
        // 监听订单的菜数量变化
        viewModel.getOrderDishNumberChangeEvent().observe(this, new Observer<OrderDishItemViewModel>() {
            @Override
            public void onChanged(OrderDishItemViewModel orderDishItemViewModel) {
                // 更新单个菜的UI
                if ("0".equals(orderDishItemViewModel.getOrderDishNumber().get())) {
                    orderDishItemViewModel.getOrderDishNumberVisibility().set(View.INVISIBLE);
                    orderDishItemViewModel.getReduceButtonVisibility().set(View.INVISIBLE);
                }
                else {
                    orderDishItemViewModel.getOrderDishNumberVisibility().set(View.VISIBLE);
                    orderDishItemViewModel.getReduceButtonVisibility().set(View.VISIBLE);
                }
                // 更新底部导航栏UI
                viewModel.getOrderDishTotalPrice().set(MathUtils.doubleKeepTwoToString
                        (viewModel.getOrder().getOrderTotalPrice()));
                if (viewModel.getOrder().getOrderDishNumber() == 0) {
                    viewModel.getPayButton().setPayable(false);
                }
                else {
                    viewModel.getPayButton().setPayable(true);
                }
            }
        });
    }
}










