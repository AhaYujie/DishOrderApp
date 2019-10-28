package com.aha.dishordersystem.data.db.model.order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.aha.dishordersystem.data.db.model.dish.Dish;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 订单菜
 */
public class OrderDish extends LitePalSupport implements Parcelable {

    @Column(nullable = false)
    private int dishServerId;

    @Column(nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private int dishNumber;

    public int getDishServerId() {
        return dishServerId;
    }

    public void setDishServerId(int dishServerId) {
        this.dishServerId = dishServerId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(dishServerId);
        parcel.writeParcelable(dish, i);
        parcel.writeInt(dishNumber);
    }

    protected OrderDish(Parcel in) {
        dishServerId = in.readInt();
        dish = in.readParcelable(Dish.class.getClassLoader());
        dishNumber = in.readInt();
    }

    public static final Creator<OrderDish> CREATOR = new Creator<OrderDish>() {
        @Override
        public OrderDish createFromParcel(Parcel parcel) {
            return new OrderDish(parcel);
        }

        @Override
        public OrderDish[] newArray(int i) {
            return new OrderDish[i];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "dish_number: " + dishNumber + ", " +
                "dish: " + dish;
    }
}
