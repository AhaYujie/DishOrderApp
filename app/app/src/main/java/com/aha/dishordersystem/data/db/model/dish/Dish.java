package com.aha.dishordersystem.data.db.model.dish;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 菜
 */
public class Dish extends LitePalSupport implements Parcelable, Cloneable {

    @Column(unique = true)
    private int serverId;   // 服务器数据库id

    @Column(nullable = false)
    private String dishName;

    @Column(nullable = false)
    private double dishPrice;

    private String dishImageUrl;

    private String dishDetail;

    @Column(nullable = false)
    private DishCategory dishCategory = new DishCategory();

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

    public String getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(String dishDetail) {
        this.dishDetail = dishDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(serverId);
        parcel.writeString(dishName);
        parcel.writeDouble(dishPrice);
        parcel.writeString(dishImageUrl);
        parcel.writeString(dishDetail);
        parcel.writeParcelable(dishCategory, i);
    }

    public Dish() {}

    protected Dish(Parcel in) {
        serverId = in.readInt();
        dishName = in.readString();
        dishPrice = in.readDouble();
        dishImageUrl = in.readString();
        dishDetail = in.readString();
        dishCategory = in.readParcelable(DishCategory.class.getClassLoader());
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel parcel) {
            return new Dish(parcel);
        }

        @Override
        public Dish[] newArray(int i) {
            return new Dish[i];
        }
    };

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        Dish clone = (Dish) super.clone();
        clone.dishCategory = (DishCategory) dishCategory.clone();
        return clone;
    }

    @NonNull
    @Override
    public String toString() {
        return "server_id: " + serverId + ", " +
                "dish_name: " + dishName + ", " +
                "dish_price: " + dishPrice + ", " +
                "dish_image_url: " + dishImageUrl;
    }
}
