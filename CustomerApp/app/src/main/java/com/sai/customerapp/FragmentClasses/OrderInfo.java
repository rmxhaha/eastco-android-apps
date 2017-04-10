package com.sai.customerapp.FragmentClasses;

/**
 * Created by AlbertusK95 on 4/10/2017.
 */

public class OrderInfo {

    public String orderMenuName;
    public int orderMenuQuantity;
    public int orderMenuPrice;
    public String orderMenuAddress;

    public OrderInfo() {

    }

    public OrderInfo(String orderMenuName, int orderMenuQuantity, int orderMenuPrice, String orderMenuAddress) {
        this.orderMenuName = orderMenuName;
        this.orderMenuQuantity = orderMenuQuantity;
        this.orderMenuPrice = orderMenuPrice;
        this.orderMenuAddress = orderMenuAddress;
    }

}
