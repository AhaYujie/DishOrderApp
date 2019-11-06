package com.aha.dishordersystem.data.db.model.order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class HistoryOrder extends LitePalSupport implements Parcelable, Cloneable {

    public static final int FINISHED = 1;

    public static final int UN_FINISH = 0;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private Date orderCreateTime;

    @Column(nullable = false)
    private int orderIsFinish;  // 订单完成标记(0为未完成, 1为已完成)

    @Column(nullable = false)
    private int orderDishNumber;

    @Column(nullable = false)
    private double orderTotalPrice;

    @Column(nullable = false)
    private List<OrderDish> orderDishes = new ArrayList<>();

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getOrderIsFinish() {
        return orderIsFinish;
    }

    public void setOrderIsFinish(int orderIsFinish) {
        this.orderIsFinish = orderIsFinish;
    }

    public int getOrderDishNumber() {
        return orderDishNumber;
    }

    public void setOrderDishNumber(int orderDishNumber) {
        this.orderDishNumber = orderDishNumber;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderCreateTime.toString());
        parcel.writeInt(orderIsFinish);
        parcel.writeInt(orderDishNumber);
        parcel.writeDouble(orderTotalPrice);
        parcel.writeTypedList(orderDishes);
    }

    public  HistoryOrder() {}

    protected HistoryOrder(Parcel in) {
        orderCreateTime = new Date(in.readString());
        orderIsFinish = in.readInt();
        orderDishNumber = in.readInt();
        orderTotalPrice = in.readDouble();
        in.readTypedList(orderDishes, OrderDish.CREATOR);
    }

    public static final Creator<HistoryOrder> CREATOR = new Creator<HistoryOrder>() {
        @Override
        public HistoryOrder createFromParcel(Parcel parcel) {
            return new HistoryOrder(parcel);
        }

        @Override
        public HistoryOrder[] newArray(int i) {
            return new HistoryOrder[i];
        }
    };

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        HistoryOrder clone = (HistoryOrder) super.clone();
        clone.orderCreateTime = (Date) orderCreateTime.clone();
        clone.orderDishes = new ArrayList<>();
        for (OrderDish orderDish : orderDishes) {
            clone.orderDishes.add((OrderDish) orderDish.clone());
        }
        return clone;
    }

    @NonNull
    @Override
    public String toString() {
        return "orderCreateTime: " + orderCreateTime + ", " +
                "orderIsFinish: " + orderIsFinish + ", " +
                "orderDishNumber: " + orderDishNumber + ", " +
                "orderTotalPrice: " + orderTotalPrice + ", " +
                "orderDishes: " + orderDishes;
    }
}
