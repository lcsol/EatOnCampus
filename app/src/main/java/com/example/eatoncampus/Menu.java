package com.example.eatoncampus;

public class Menu {
    public int menuID;
    public static String menuName;
    public static double menuPrice;
    public static String menu_RID;

    public Menu(int menuID, String menuName, double menuPrice, String menu_RID) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menu_RID = menu_RID;
    }

    public Menu(String menuName, double menuPrice, String menu_RID) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menu_RID = menu_RID;
    }
}
