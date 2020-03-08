package com.example.eatoncampus;

public class Restaurant {
    public String restaurantID;
    public static String restaurantName;
    public static String restaurantAddress;
    public static String restaurantHours;
    public static int restaurantCode;

    public Restaurant(String restaurantID, String restaurantName, String restaurantAdress, String restaurantHours, int restaurantCode) {
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAdress;
        this.restaurantHours = restaurantHours;
        this.restaurantCode = restaurantCode;
    }

    public Restaurant( String restaurantName, String restaurantAdress, String restaurantHours, int restaurantCode) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAdress;
        this.restaurantHours = restaurantHours;
        this.restaurantCode = restaurantCode;
    }

    public void setRestaurantID(String id) {
        this.restaurantID = id;
    }

    public String getRestaurantID() {
        return restaurantID;
    }



}
