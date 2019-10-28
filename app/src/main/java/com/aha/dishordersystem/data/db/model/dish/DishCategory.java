package com.aha.dishordersystem.data.db.model.dish;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜类别
 */
public class DishCategory extends LitePalSupport implements Parcelable {

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false)
    private List<Dish> dishes = new ArrayList<>();

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryName);
        parcel.writeTypedList(dishes);
    }

    protected DishCategory(Parcel in) {
        categoryName = in.readString();
        in.readTypedList(dishes, Dish.CREATOR);
    }

    public static final Creator<DishCategory> CREATOR = new Creator<DishCategory>() {
        @Override
        public DishCategory createFromParcel(Parcel parcel) {
            return new DishCategory(parcel);
        }

        @Override
        public DishCategory[] newArray(int i) {
            return new DishCategory[i];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return categoryName + ":" + dishes;
    }
}
