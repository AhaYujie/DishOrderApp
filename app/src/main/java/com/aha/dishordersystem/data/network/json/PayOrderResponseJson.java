package com.aha.dishordersystem.data.network.json;

public class PayOrderResponseJson {

    public static final int PAY_ORDER_SUCCESS = 1;

    public static final int PAY_ORDER_ERROR = 0;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
