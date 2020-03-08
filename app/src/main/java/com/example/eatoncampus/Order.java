package com.example.eatoncampus;

public class Order {
    public int order_id;
    public static String order_stu_id;
    public static double order_price;
    public static String order_time;
    public static String pickup_time;
    public static String order_status;

    public Order(int order_id, String order_stu_id, double order_price, String order_time, String pickup_time, String order_status) {
        this.order_id = order_id;
        this.order_stu_id = order_stu_id;
        this.order_price = order_price;
        this.order_time = order_time;
        this.pickup_time = pickup_time;
        this.order_status = order_status;
    }

    public Order(String order_stu_id, double order_price, String order_time, String pickup_time, String order_status) {
        this.order_stu_id = order_stu_id;
        this.order_price = order_price;
        this.order_time = order_time;
        this.pickup_time = pickup_time;
        this.order_status = order_status;
    }
}
