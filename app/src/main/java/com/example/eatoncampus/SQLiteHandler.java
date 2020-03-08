package com.example.eatoncampus;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TreeSet;

import android.content.ContentValues;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 7;

    private static final String DATABASE_NAME = "EatOnCampusDB";

    // Student table
    private static final String TABLE_STUDENT = "Student";
    private static final String STUDENT_ID = "Student_ID";
    private static final String STUDENT_FNAME = "Student_First_Name";
    private static final String STUDENT_LNAME = "Student_Last_Name";
    private static final String STUDENT_PASSWORD = "Student_Password";

    // Manager table
    private static final String TABLE_MANAGER = "Manager";
    private static final String MANAGER_ID = "Manager_ID";
    private static final String MANAGER_FNAME = "Manager_First_Name";
    private static final String MANAGER_LNAME = "realManager_Last_Name_name";
    private static final String MANAGER_R_ID = "Manager_Restaurant_ID";
    private static final String MANAGER_PASSWORD = "Manager_Password";

    // Restaurant table
    private static final String TABLE_RESTAURANT = "Restaurant";
    private static final String RESTAURANT_ID = "Restaurant_ID";
    private static final String RESTAURANT_NAME = "Name";
    private static final String RESTAURANT_ADDRESS = "Address";
    private static final String RESTAURANT_HOURS = "Hours";
    private static final String RESTAURANT_CODE = "Code";

    // Menu table
    private static final String TABLE_MENU = "Menu";
    private static final String MENU_ID = "Menu_id";
    private static final String MENU_NAME = "Menu_name";
    private static final String MENU_PRICE = "Menu_price";
    private static final String MENU_R_ID = "Restaurant_ID";

    // OrderActivity table
    private static final String TABLE_ORDER = "Orders";
    private static final String ORDER_ID = "Order_id";
    private static final String ORDER_STUDENT_ID = "Student_id";
    private static final String ORDER_PRICE = "Order_price";
    private static final String ORDER_TIME = "Order_time";
    private static final String ORDER_PICKUP_TIME = "Pickup_time";
    private static final String ORDER_STATUS = "Order_status";

    // Order_Menu table
    private static final String TABLE_ORDER_MENU = "Order_menu";
    private static final String ORDER_MENU_OID = "Order_id";
    private static final String ORDER_MENU_MID = "Menu_id";

    // Student table create statement
    private static final String CREATE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " ("
            + STUDENT_ID + " TEXT PRIMARY KEY, "
            + STUDENT_FNAME + " TEXT NOT NULL, "
            + STUDENT_LNAME + " TEXT NOT NULL, "
            + STUDENT_PASSWORD + " TEXT NOT NULL );";

    // Manager table create statement
    private static final String CREATE_MANAGER = "CREATE TABLE " + TABLE_MANAGER + " ("
            + MANAGER_ID + " TEXT PRIMARY KEY, "
            + MANAGER_FNAME + " TEXT NOT NULL, "
            + MANAGER_LNAME + " TEXT NOT NULL, "
            + MANAGER_R_ID + " TEXT NOT NULL, "
            + MANAGER_PASSWORD + " TEXT NOT NULL, "
            + " FOREIGN KEY (" + MANAGER_R_ID + ") REFERENCES " + TABLE_RESTAURANT + "(" + RESTAURANT_ID + "));";

    // Restaurant table create statement
    private static final String CREATE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + " ("
            + RESTAURANT_ID + " TEXT PRIMARY KEY, "
            + RESTAURANT_NAME + " TEXT NOT NULL UNIQUE, "
            + RESTAURANT_ADDRESS + " TEXT NOT NULL, "
            + RESTAURANT_HOURS + " TEXT NOT NULL, "
            + RESTAURANT_CODE + " TEXT NOT NULL );";

    // Menu table create statement
    private static final String CREATE_MENU = "CREATE TABLE " + TABLE_MENU + " ("
            + MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MENU_NAME + " TEXT NOT NULL, "
            + MENU_PRICE + " TEXT NOT NULL, "
            + MENU_R_ID + " TEXT NOT NULL, "
            + " FOREIGN KEY (" + MENU_R_ID + ") REFERENCES " + TABLE_RESTAURANT + "(" + RESTAURANT_ID + "));";

    // Order table create statement
    private static final String CREATE_ORDER = "CREATE TABLE " + TABLE_ORDER + " ("
            + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ORDER_STUDENT_ID + " TEXT, "
            + ORDER_PRICE + " REAL, "
            + ORDER_TIME + " TEXT NOT NULL, "
            + ORDER_PICKUP_TIME + " TEXT, "
            + ORDER_STATUS + " TEXT NOT NULL, "
            + " FOREIGN KEY (" + ORDER_STUDENT_ID + ") REFERENCES " + TABLE_STUDENT + "(" + STUDENT_ID + "));";

    // Order_Menu table create statement
    private static final String CREATE_ORDER_MENU = "CREATE TABLE " + TABLE_ORDER_MENU + " ("
            + ORDER_MENU_OID + " INTEGER, "
            + ORDER_MENU_MID + " TEXT, "
            + "PRIMARY KEY (" + ORDER_MENU_OID + ", " + ORDER_MENU_MID + "),"
            + " FOREIGN KEY (" + ORDER_MENU_OID + ") REFERENCES " + TABLE_ORDER + "(" + ORDER_ID + "),"
            + " FOREIGN KEY (" + ORDER_MENU_MID + ") REFERENCES " + TABLE_MENU + "(" + MENU_ID + "));";


//    private static final String[] RESTAURANT_COLUMNS = {RESTAURANT_ID, RESTAURANT_ADDRESS, RESTAURANT_HOURS};
//
//    private static final String[] RESULT_COLUMNS = {KEY_RESULT_QUIZ_NAME, KEY_PLAYER_NAME, KEY_PERCENTAGE, KEY_FINISH_TIME};


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_MANAGER);
        db.execSQL(CREATE_RESTAURANT);
        db.execSQL(CREATE_MENU);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_ORDER_MENU);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANAGER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_MENU);

        this.onCreate(db);
    }
    // ===== Create ===== //

    // add student info
    public boolean addStudent(String studentID, String studentFname, String studentLname, String studentPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(STUDENT_ID, studentID);
        values.put(STUDENT_FNAME, studentFname);
        values.put(STUDENT_LNAME, studentLname);
        values.put(STUDENT_PASSWORD, studentPassword);

        long newRowId = db.insert(TABLE_STUDENT, null, values);
        return newRowId != -1;
    }

    // add manager info
    public boolean addManager(String managerID, String managerFname, String managerLname, String manager_RID, String managerPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MANAGER_ID, managerID);
        values.put(MANAGER_FNAME, managerFname);
        values.put(MANAGER_LNAME, managerLname);
        values.put(MANAGER_R_ID, manager_RID);
        values.put(MANAGER_PASSWORD, managerPassword);

        long newRowId = db.insert(TABLE_MANAGER, null, values);
        return newRowId != -1;
    }

//    // add restaurant info
//    public boolean addRestaurant(int restaurantID, String restaurantName, String restaurantAddress, String restaurantHours, int restaurantCode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(RESTAURANT_ID, restaurantID);
//        values.put(RESTAURANT_NAME, restaurantName);
//        values.put(RESTAURANT_ADDRESS, restaurantAddress);
//        values.put(RESTAURANT_HOURS, restaurantHours);
//        values.put(RESTAURANT_CODE, restaurantCode);
//
//        long newRowId = db.insert(TABLE_RESTAURANT, null, values);
//        return newRowId != -1;
//    }

    // add restaurant info
    public boolean addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RESTAURANT_ID, restaurant.restaurantID);
        values.put(RESTAURANT_NAME, restaurant.restaurantName);
        values.put(RESTAURANT_ADDRESS, restaurant.restaurantAddress);
        values.put(RESTAURANT_HOURS, restaurant.restaurantHours);
        values.put(RESTAURANT_CODE, restaurant.restaurantCode);

        long newRowId = db.insert(TABLE_RESTAURANT, null, values);
        db.close();
        return newRowId != -1;
    }

    // add menu info
    public boolean addMenu(String menuName, String menuPrice, String menu_RID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(MENU_ID, menuID);
        values.put(MENU_NAME, menuName);
        values.put(MENU_PRICE, menuPrice);
        values.put(MENU_R_ID, menu_RID);

        long newRowId = db.insert(TABLE_MENU, null, values);
        db.close();
        return newRowId != -1;
    }

//    // add menu info
//    public boolean addMenu(Menu menu) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
////        values.put(MENU_ID, menu.menuID);
//        values.put(MENU_NAME, menu.menuName);
//        values.put(MENU_PRICE, menu.menuPrice);
//        values.put(MENU_R_ID, menu.menu_RID);
//
//        long newRowId = db.insert(TABLE_MENU, null, values);
//        db.close();
//        return newRowId != -1;
//    }

    // create order info
    public int addOrder(String order_stu_id, double order_price, String order_time, String pickup_time, String order_status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORDER_STUDENT_ID, order_stu_id);
        values.put(ORDER_PRICE, order_price);
        values.put(ORDER_TIME, order_time);
        values.put(ORDER_PICKUP_TIME, pickup_time);
        values.put(ORDER_STATUS, order_status);

        long newRowId = db.insert(TABLE_ORDER, null, values);
        return (int)newRowId;
    }
//    // create order info
//    public boolean addOrder(Order order) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
////        values.put(ORDER_ID, order.order_id);
//        values.put(ORDER_STUDENT_ID, order.order_stu_id);
//        values.put(ORDER_PRICE, order.order_price);
//        values.put(ORDER_TIME, order.order_time);
//        values.put(ORDER_PICKUP_TIME, order.pickup_time);
//        values.put(ORDER_STATUS, order.order_status);
//
//        long newRowId = db.insert(TABLE_ORDER, null, values);
//        db.close();
//        return newRowId != -1;
//    }

    // add to order_menu table
    public boolean add_to_order_menu(int order_id, String menu_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORDER_MENU_OID, order_id);
        values.put(ORDER_MENU_MID, menu_id);

        long newRowId = db.insert(TABLE_ORDER_MENU, null, values);
        db.close();
        return newRowId != -1;
    }

    // ===== Check ===== //

    // check if input student id exist
    public boolean checkStudentID(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[]{STUDENT_ID}, STUDENT_ID + " = ?", new String[]{studentID},
                null, null, null, null);
        return cursor.getCount() > 0;
    }

    // check if input manager id exist
    public boolean checkManagerID(String managerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MANAGER, new String[]{MANAGER_ID}, MANAGER_ID + " = ?", new String[]{managerID},
                null, null, null, null);
        return cursor.getCount() > 0;
    }

    // check if password match userID
    public boolean checkPassword(String userType, String userID, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String userPassword;
        if (userType.equals("Student")) {
            cursor = db.query(TABLE_STUDENT, new String[]{STUDENT_PASSWORD}, STUDENT_ID + " = ?", new String[]{userID},
                    null, null, null, null);
            cursor.moveToFirst();
            userPassword = cursor.getString(cursor.getColumnIndex(STUDENT_PASSWORD));
        } else {
            cursor = db.query(TABLE_MANAGER, new String[]{MANAGER_ID, MANAGER_PASSWORD}, MANAGER_ID + " = ?", new String[]{userID},
                    null, null, null, null);
            cursor.moveToFirst();
            userPassword = cursor.getString(cursor.getColumnIndex(MANAGER_PASSWORD));
        }
        return userPassword.equals(password);
    }

    public boolean checkRestauntID(String restaurant_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANT, new String[]{RESTAURANT_ID}, RESTAURANT_ID + " = ?", new String[]{restaurant_id},
                null, null, null, null);
        return cursor.getCount() > 0;
    }

    public boolean checkMenuID(String menu_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU, new String[]{MENU_ID}, MENU_ID + " = ?", new String[]{menu_id},
                null, null, null, null);
        return cursor.getCount() > 0;
    }


    // ===== GET ===== //

    public List<String> getAllRestaurants() {
        List<String> restaurants = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_RESTAURANT;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                restaurants.add(cursor.getString(cursor.getColumnIndex(RESTAURANT_NAME)));
            } while (cursor.moveToNext());

        }
        return restaurants;
    }

    public String get_restaurantName_by_mID(String manager_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + RESTAURANT_NAME
                + " FROM " + TABLE_RESTAURANT + ", " + TABLE_MANAGER
                + " WHERE " + MANAGER_ID + " = " + manager_id
                + " AND " + RESTAURANT_ID + " = " + MANAGER_R_ID;
        Cursor cursor = db.rawQuery(query, null);

        String id = "none";
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex(RESTAURANT_NAME));
            } while (cursor.moveToNext());
        }

        db.close();
        return id;
    }

    public String get_restaurantID_by_mID(String manager_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + RESTAURANT_ID
                + " FROM " + TABLE_RESTAURANT + ", " + TABLE_MANAGER
                + " WHERE " + MANAGER_ID + " = " + manager_id
                + " AND " + RESTAURANT_ID + " = " + MANAGER_R_ID;
        Cursor cursor = db.rawQuery(query, null);

        String id = "none";
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex(RESTAURANT_ID));
            } while (cursor.moveToNext());
        }

        db.close();
        return id;
    }

//    public String get_restaurantID_by_resName(String restaurantName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String query = "SELECT " + RESTAURANT_ID
//                + " FROM " + TABLE_RESTAURANT
//                + " WHERE " + RESTAURANT_NAME + " = " + restaurantName;
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        cursor.moveToFirst();
//        String id = cursor.getString(cursor.getColumnIndex(RESTAURANT_ID));
//
////        String id = "none";
////        if (cursor.moveToFirst()) {
////            do {
////                id = cursor.getString(cursor.getColumnIndex(RESTAURANT_ID));
////            } while (cursor.moveToNext());
////        }
//
//        db.close();
//        return id;
//    }


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Get info of the restaurant
    public List<List<String>> getRestaurantInfo(String restaurantName) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<String>> res = new ArrayList<>();
        List<String> resInfo = new ArrayList<>();
        List<String> menu_id_list = new ArrayList<>();
        List<String> menuName_list = new ArrayList<>();
        List<String> menuPrice_list = new ArrayList<>();
//        System.out.print(userName);
        Cursor restaurant_cursor, menu_cursor;
        String restaurant_id;
        String menu_id, restaurant_hours, restaurant_address, menuName, menuPrice;

        try {
            restaurant_cursor = db.query(TABLE_RESTAURANT, new String[]{RESTAURANT_ID, RESTAURANT_ADDRESS, RESTAURANT_HOURS},
                    RESTAURANT_NAME + " = ?", new String[]{restaurantName},
                    null, null, null, null);
            restaurant_cursor.moveToFirst();
            restaurant_id = restaurant_cursor.getString(restaurant_cursor.getColumnIndex(RESTAURANT_ID));
            restaurant_address = restaurant_cursor.getString(restaurant_cursor.getColumnIndex(RESTAURANT_ADDRESS));
            restaurant_hours = restaurant_cursor.getString(restaurant_cursor.getColumnIndex(RESTAURANT_HOURS));
            resInfo.add(restaurant_id);
            resInfo.add(restaurant_address);
            resInfo.add(restaurant_hours);

            String menuQuery = "SELECT " + MENU_ID + ", " + MENU_NAME + ", " + MENU_PRICE
                    + " FROM " + TABLE_MENU
                    + " WHERE " + MENU_R_ID + " = " + restaurant_id;
            menu_cursor = db.rawQuery(menuQuery, null);

            if (menu_cursor.moveToFirst()) {
                do {
                    menu_id = menu_cursor.getString(menu_cursor.getColumnIndex(MENU_ID));
                    menuName = menu_cursor.getString(menu_cursor.getColumnIndex(MENU_NAME));
                    menuPrice = menu_cursor.getString(menu_cursor.getColumnIndex(MENU_PRICE));
                    menu_id_list.add(menu_id);
                    menuName_list.add(menuName);
                    menuPrice_list.add(menuPrice);
                } while (menu_cursor.moveToNext());
            }
//            cursor.close();

        }catch (SQLiteException e)
        {
            System.out.print("Retrieve restaurant info failed");
        }
        res.add(resInfo);
        res.add(menu_id_list);
        res.add(menuName_list);
        res.add(menuPrice_list);
        return res;
    }

    public List<String> getRestaurantOrder(String menu_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<String> order_id_list = new ArrayList<>();

        Cursor cursor;

        String order_id;

        try {
            String query = "SELECT " + ORDER_MENU_OID
                    + " FROM " + TABLE_ORDER_MENU
                    + " WHERE " + ORDER_MENU_MID + " = " + menu_id;

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    order_id = cursor.getString(cursor.getColumnIndex(ORDER_MENU_OID));
                    order_id_list.add(order_id);

                } while (cursor.moveToNext());
            }

        }catch (SQLiteException e)
        {
            System.out.print("Retrieve restaurant info failed");
        }

        return order_id_list;
    }

    // Get restaurant orders info
    public List<String> getRestaurantOrderInfo(String order_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> res = new ArrayList<>();

        Cursor cursor;

        String order_time, order_status;

        try {
            String query = "SELECT " + ORDER_TIME + ", " + ORDER_STATUS
                    + " FROM " + TABLE_ORDER
                    + " WHERE " + ORDER_ID + " = " + order_id;

            cursor = db.rawQuery(query, null);

            cursor.moveToFirst();
            order_time = cursor.getString(cursor.getColumnIndex(ORDER_TIME));
            order_status = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
            res.add(order_time);
            res.add(order_status);
//            cursor.close();

        }catch (SQLiteException e)
        {
            System.out.print("Retrieve my order info failed");
        }

        return res;
    }

    // Get current student orders
    public List<List<String>> getCurrentOrderInfo(String student_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<String>> res = new ArrayList<>();
        List<String> my_orders_id = new ArrayList<>();
        List<String> my_orders_time = new ArrayList<>();
        List<String> my_orders_price = new ArrayList<>();
        List<String> my_orders_status = new ArrayList<>();

        Cursor cursor;

        String order_id, order_time, order_price, order_status;

        try {
            String query = "SELECT " + ORDER_ID + ", " + ORDER_TIME + ", " + ORDER_PRICE + ", " + ORDER_STATUS
                    + " FROM " + TABLE_ORDER
                    + " WHERE " + ORDER_STUDENT_ID + " = " + student_id;
//                    + " AND " + ORDER_STATUS + " = " + ;

//                    + " OR " + ORDER_STATUS + " = " + "Accepted"
//                    + " OR " + ORDER_STATUS + " = " + "Ready";

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    order_id = cursor.getString(cursor.getColumnIndex(ORDER_ID));
                    order_time = cursor.getString(cursor.getColumnIndex(ORDER_TIME));
                    order_price = cursor.getString(cursor.getColumnIndex(ORDER_PRICE));
                    order_status = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
                    my_orders_id.add(order_id);
                    my_orders_time.add(order_time);
                    my_orders_price.add(order_price);
                    my_orders_status.add(order_status);
                } while (cursor.moveToNext());
            }
//            cursor.close();

        }catch (SQLiteException e)
        {
            System.out.print("Retrieve my order info failed");
        }
        res.add(my_orders_id);
        res.add(my_orders_time);
        res.add(my_orders_price);
        res.add(my_orders_status);
        return res;
    }

    // Get previous student orders
    public List<List<String>> getOldOrderInfo(String student_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<String>> res = new ArrayList<>();
        List<String> my_orders_id = new ArrayList<>();
        List<String> my_orders_time = new ArrayList<>();
        List<String> my_orders_price = new ArrayList<>();
        List<String> my_orders_status = new ArrayList<>();

        Cursor cursor;

        String order_id, order_time, order_price, order_status;

        try {
            String query = "SELECT " + ORDER_ID + ", " + ORDER_TIME + ", " + ORDER_PRICE + ", " + ORDER_STATUS
                    + " FROM " + TABLE_ORDER
                    + " WHERE " + ORDER_STUDENT_ID + " = " + student_id
                    + " AND " + ORDER_STATUS + " = " + "Closed"
                    + " OR " + ORDER_STATUS + " = " + "Canceled";

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    order_id = cursor.getString(cursor.getColumnIndex(ORDER_ID));
                    order_time = cursor.getString(cursor.getColumnIndex(ORDER_TIME));
                    order_price = cursor.getString(cursor.getColumnIndex(ORDER_PRICE));
                    order_status = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
                    my_orders_id.add(order_id);
                    my_orders_time.add(order_time);
                    my_orders_price.add(order_price);
                    my_orders_status.add(order_status);
                } while (cursor.moveToNext());
            }
//            cursor.close();

        }catch (SQLiteException e)
        {
            System.out.print("Retrieve my previous order info failed");
        }
        res.add(my_orders_id);
        res.add(my_orders_time);
        res.add(my_orders_price);
        res.add(my_orders_status);
        return res;
    }

    public List<String> get_orderInfo_by_orderID(String order_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> res = new ArrayList<>();

        String order_time = "", order_price = "", order_status = "";

        try {
            String query = "SELECT " + ORDER_TIME + ", " + ORDER_PRICE + ", " + ORDER_STATUS
                    + " FROM " + TABLE_ORDER
                    + " WHERE " + ORDER_ID + " = " + order_id;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    order_time = cursor.getString(cursor.getColumnIndex(ORDER_TIME));
                    order_price = cursor.getString(cursor.getColumnIndex(ORDER_PRICE));
                    order_status = cursor.getString(cursor.getColumnIndex(ORDER_STATUS));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e)
        {
            System.out.print("Retrieve order info failed");
        }
//        db.close();
        res.add(order_time);
        res.add(order_price);
        res.add(order_status);
        return res;
    }

    public List<List<String>> get_menuInfo_by_orderID(String order_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<String>> res = new ArrayList<>();

        List<String> menu_name_list = new ArrayList<>();
        List<String> menu_price_list = new ArrayList<>();

        String menu_name, menu_price, menu_order_mid;
        List<String> menu_order_mid_list = new ArrayList<>() ;

        try {

//            String query = "SELECT " + MENU_NAME + ", " + MENU_PRICE
//                    + " FROM " + TABLE_ORDER + ", " + TABLE_MENU + ", " + TABLE_ORDER_MENU
//                    + " WHERE " + ORDER_ID + " = " + ORDER_MENU_OID
//                    + " AND " + MENU_ID + " = " + ORDER_MENU_MID
//                    + " AND " + ORDER_ID + " = " + order_id;

            String menu_order_mid_query = "SELECT * FROM " + TABLE_ORDER_MENU
                    + " WHERE " + ORDER_MENU_OID + " = " + order_id;
            Cursor menu_order_mid_cursor = db.rawQuery(menu_order_mid_query, null);

            Cursor cursor;

            if (menu_order_mid_cursor.moveToFirst()) {
                do {
                    menu_order_mid = menu_order_mid_cursor.getString(menu_order_mid_cursor.getColumnIndex(ORDER_MENU_MID));

                    String query = "SELECT " + MENU_NAME + ", " + MENU_PRICE
                            + " FROM " + TABLE_MENU
                            + " WHERE " + MENU_ID + " = " + menu_order_mid;

                    cursor = db.rawQuery(query, null);

                    if (cursor.moveToFirst()) {
                        do {
                            menu_name = cursor.getString(cursor.getColumnIndex(MENU_NAME));
                            menu_price = cursor.getString(cursor.getColumnIndex(MENU_PRICE));
                            menu_name_list.add(menu_name);
                            menu_price_list.add(menu_price);
                        } while (cursor.moveToNext());
                    }

                    menu_order_mid_list.add(menu_order_mid);
                } while (menu_order_mid_cursor.moveToNext());
            }

        } catch (SQLiteException e)
        {
            System.out.print("Retrieve order info failed");
        }

//        db.close();
        res.add(menu_name_list);
        res.add(menu_price_list);
        return res;
    }


    // ======= EDIT ========== //

    // update menu info
    public boolean editMenu(String menuID, String menuName, String menuPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MENU_NAME, menuName);
        values.put(MENU_PRICE, menuPrice);

        int newRowId = db.update(TABLE_MENU, values, MENU_ID + " = " + menuID, null);

        return newRowId > 0;
    }

    // update restaurant info
    public boolean updateInfo(String restaurant_id, String address, String hours) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RESTAURANT_ADDRESS, address);
        values.put(RESTAURANT_HOURS, hours);

        int newRowId = db.update(TABLE_RESTAURANT, values, RESTAURANT_ID + " = " + restaurant_id, null);

        return newRowId > 0;
    }

    // update order status
    public boolean updateOrder(String order_id, String order_status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORDER_STATUS, order_status);

        int newRowId = db.update(TABLE_ORDER, values, ORDER_ID + " = " + order_id, null);

        return newRowId > 0;
    }



// ======= DELETE ========== //

    public void deleteRestaurant() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, null, null);
        db.close();
    }

    public void deleteManager() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MANAGER, null, null);
        db.close();
    }

    public void deleteOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, null, null);
        db.close();
    }

    public void deleteOrderMenu() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER_MENU, null, null);
        db.close();
    }


}
