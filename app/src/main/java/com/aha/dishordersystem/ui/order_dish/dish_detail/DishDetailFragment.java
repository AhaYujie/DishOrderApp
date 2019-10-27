package com.aha.dishordersystem.ui.order_dish.dish_detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.databinding.FragmentDishDetailBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;


public class DishDetailFragment extends BaseFragment<FragmentDishDetailBinding, DishDetailViewModel> {

    private Bundle dishData;

    /**
     * 启动 DishDetailFragment
     * @param viewModel
     * @param dishName
     * @param dishImageUrl
     * @param dishPrice
     * @param dishDetail
     * @param orderDishNumber
     */
    public static void actionStart(BaseViewModel viewModel, String dishName, String dishImageUrl,
                                   String dishPrice, String dishDetail, String orderDishNumber) {
        Bundle dishData = new Bundle();
        dishData.putString("dish_name", dishName);
        dishData.putString("dish_image_url", dishImageUrl);
        dishData.putString("dish_price", dishPrice);
        dishData.putString("dish_detail", dishDetail);
        dishData.putString("order_dish_number", orderDishNumber);
        viewModel.startContainerActivity(DishDetailFragment.class.getCanonicalName(), dishData);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_dish_detail;
    }

    @Override
    public int initVariableId() {
        return BR.dishDetailViewModel;
    }

    @Override
    public void initParam() {
        dishData = getArguments();
    }

    @Override
    public void initData() {
        viewModel.initData(dishData);
    }

}
