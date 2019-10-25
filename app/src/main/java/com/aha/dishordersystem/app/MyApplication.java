package com.aha.dishordersystem.app;

import android.content.Context;
import android.util.Log;

import com.aha.dishordersystem.BuildConfig;
import com.aha.dishordersystem.R;

import org.litepal.LitePal;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;

public class MyApplication extends BaseApplication {

    private static final String TAG = "ahayujie";

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        // 打印日志
        KLog.init(BuildConfig.DEBUG);
    }

    public static String getTAG() {
        return TAG;
    }

}
