package com.example.eatoncampus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    EditText userID_input, password_input;
    Button logIn_button, register_button;
    Spinner user_type_spinner;
    String[] user_type_list = {"Select Your Identity", "Student", "Manager"};
    String userID, password, userType;
    SQLiteHandler db;
    public int restaurantID, menuID, order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longin);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        userID_input = findViewById(R.id.userID_input);
        password_input = findViewById(R.id.password_input);
        logIn_button = findViewById(R.id.logIn_button);
        register_button = findViewById(R.id.register_button);
        db = new SQLiteHandler(this);
        userType = "";

        user_type_spinner = findViewById(R.id.user_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, user_type_list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;
                if (position == 0) text.setTextColor(Color.GRAY); // set hint text color to gray
                else text.setTextColor(Color.BLACK);
                return view;
            }
        };

        user_type_spinner.setAdapter(adapter);
        user_type_spinner.setSelection(0); // set hint of spinner

        user_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                userType = pos == 0 ? "" : user_type_spinner.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        db.deleteOrders();

//        db.deleteManager();

//        db.deleteRestaurant();
        // add restaurants info
//        Restaurant pizza_store = new Restaurant( "001","Pizza Store", "001 Food St", "09:00:00 - 18:00:00", 001);
//        Restaurant burger_store = new Restaurant("002", "Burger Store", "002 Food St", "10:00:00 - 19:00:00", 002);
//        Restaurant salad_store = new Restaurant("003", "Salad Store", "003 Food St", "11:00:00 - 20:00:00", 003);
//        if (db.addRestaurant(pizza_store)) Toast.makeText(this, "added pizza!", Toast.LENGTH_SHORT).show();
//        if (db.addRestaurant(burger_store)) Toast.makeText(this, "added burger!", Toast.LENGTH_SHORT).show();;
//        if (db.addRestaurant(salad_store)) Toast.makeText(this, "added salad!", Toast.LENGTH_SHORT).show();;

//        // add menu info

//        Menu pizza1 = new Menu("Pepperoni pizza", 5.00, pizza_store.getRestaurantID());
//        Menu pizza2 = new Menu("Cheese pizza", 3.00, pizza_store.getRestaurantID());
//        Menu pizza3 = new Menu("Vegetable pizza", 4.00, pizza_store.getRestaurantID());
//
//        Menu burger1 = new Menu("Beef burger", 6.00, burger_store.restaurantID);
//        Menu burger2 = new Menu("Chicken burger", 5.00, burger_store.restaurantID);
//        Menu burger3 = new Menu("Fries", 2.00, burger_store.restaurantID);
//
//        Menu salad1 = new Menu("Caesar Salad", 3.50, salad_store.restaurantID);
//        Menu salad2 = new Menu("Green Salad", 3.00, salad_store.restaurantID);
//        Menu salad3 = new Menu("Pasta Salad", 3.30, salad_store.restaurantID);
//        if (!db.checkMenu("Pepperoni pizza")) db.addMenu(pizza1);
//        db.addMenu(pizza1);
//        db.addMenu(pizza2);
//        db.addMenu(pizza3);
//        db.addMenu(burger1);
//        db.addMenu(burger2);
//        db.addMenu(burger3);
//        db.addMenu(salad1);
//        db.addMenu(salad2);
//        db.addMenu(salad3);
//
//        // add order info
//        Order order1 = new Order(Student.studentID, 11.3, "2019-11-05 12:00:00", "2019-11-05 12:30:00", "Ready");
//        Order order2 = new Order(Student.studentID, 15.8, "2019-11-30 17:00:00", "2019-11-05 17:30:00", "Ready");
//        Order order3 = new Order(Student.studentID, 12.5, "2019-12-03 12:30:00", "2019-12-03 12:55:00", "Canceled");
//        db.addOrder(order1);
//        db.addOrder(order2);
//        db.addOrder(order3);
    }

    // log into user account
    public void logIn(View view) {
        if (userType.equals("")) {
            Toast.makeText(this, "Select Your Identity!", Toast.LENGTH_SHORT).show();
            return;
        }
        userID = userID_input.getText().toString();
        password = password_input.getText().toString();

        if (validLogIn()) {
            Toast.makeText(this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
            if (userType.equals("Student")) {
                Intent studentHome = new Intent(this, StudentHomeActivity.class);
                studentHome.putExtra("student_id", userID);
                startActivity(studentHome);
            }
            else {
                Intent managerHome = new Intent(this, ManagerHomeActivity.class);
                managerHome.putExtra("manager_id", userID);
                startActivity(managerHome);
            }

        }
        else Toast.makeText(this, "LogIn Failed!", Toast.LENGTH_SHORT).show();
    }


    // go to registration
    public void goToRegister(View view) {
        if (userType.equals("")) {
            Toast.makeText(this, "Select Your Identity!", Toast.LENGTH_SHORT).show();
        }
        else if (userType.equals("Student")) {
            Intent studentRegister = new Intent(this, StudentRegisterActivity.class);
            startActivity(studentRegister);
        }
        else {
            Intent managerRegister = new Intent(this, ManagerRegisterActivity.class);
            startActivity(managerRegister);
        }
    }

    // check if login info is valid
    private boolean validLogIn() {
        if (userID.equals("")) {
            userID_input.setError("Input Your ID Number");
            return false;
        }
        if (userType.equals("Student") && !db.checkStudentID(userID) || userType.equals("Manager") && !db.checkManagerID(userID)) {
            userID_input.setError("UserID does not exist");
            return false;
        }
        if (password.equals("")) {
            password_input.setError("Input Your Password");
            return false;
        }
        if (!db.checkPassword(userType, userID, password)) {
            password_input.setError("Incorrect Password");
            return false;
        }
        return true;
    }

}
