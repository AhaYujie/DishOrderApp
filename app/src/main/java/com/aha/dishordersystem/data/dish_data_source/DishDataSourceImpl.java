package com.aha.dishordersystem.data.dish_data_source;

import android.util.Log;

import com.aha.dishordersystem.app.MyApplication;
import com.aha.dishordersystem.data.db.model.dish.DishCategory;
import com.aha.dishordersystem.data.db.dao.DishDao;
import com.aha.dishordersystem.data.network.api.DishService;
import com.aha.dishordersystem.data.network.json.DishListJson;
import com.aha.dishordersystem.util.HttpsUtils;
import com.aha.dishordersystem.util.JsonUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DishDataSourceImpl implements DishDataSource {

    private DishService dishService;

    private static volatile DishDataSourceImpl INSTANCE = null;

    public static DishDataSource getInstance(DishService dishService) {
        if (INSTANCE == null) {
            synchronized (DishDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DishDataSourceImpl(dishService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    private DishDataSourceImpl(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * 获取全部菜
     *
     * @return
     */
    @Override
    public Observable<DishListJson> getAllDishes() {
        final List<DishCategory> dishCategories = DishDao.getAllDishes();
        // 本地没有数据则从服务器获取
        if (dishCategories.isEmpty()) {
            Log.d(MyApplication.getTAG(), "from server");
            return dishService.getAllDishes(HttpsUtils.TOKEN);
        }
        else {
            Log.d(MyApplication.getTAG(), "from db");
            return Observable.create(new ObservableOnSubscribe<DishListJson>() {
                @Override
                public void subscribe(ObservableEmitter<DishListJson> emitter) throws Exception {
                    try {
                        emitter.onNext(JsonUtils.dishCategoriesToDishListJson(dishCategories));
                    }
                    catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                    finally {
                        emitter.onComplete();
                    }
                }
            });
        }
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishCategories
     */
    @Override
    public void saveDishToDB(final List<DishCategory> dishCategories) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DishDao.save(dishCategories);
                Log.d(MyApplication.getTAG(), "save thread: " + Thread.currentThread().getName());
            }
        }).start();
    }

    /**
     * 存储菜到本地数据库
     *
     * @param dishListJson
     */
    @Override
    public void saveDishToDB(DishListJson dishListJson) {
        saveDishToDB(JsonUtils.dishListJsonToDishCategories(dishListJson));
    }
}
