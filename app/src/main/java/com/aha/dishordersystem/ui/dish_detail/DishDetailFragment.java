package com.aha.dishordersystem.ui.dish_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aha.dishordersystem.BR;
import com.aha.dishordersystem.R;
import com.aha.dishordersystem.databinding.FragmentDishDetailBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


public class DishDetailFragment extends BaseFragment<FragmentDishDetailBinding, DishDetailViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_dish_detail;
    }

    @Override
    public int initVariableId() {
        return BR.dishDetailViewModel;
    }
}
