package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {
    ListView restaurantList;
    List<String> list;
    String restaurantName, studentID;
    SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        db = new SQLiteHandler(this);
        Intent stuID = getIntent();
        studentID = stuID.getStringExtra("student_id");

        restaurantList = findViewById(R.id.restaurantList);

        list = db.getAllRestaurants();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        restaurantList.setAdapter(adapter);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                restaurantName = list.get(position);
                goToOrder();
            }
        });
    }

    private void goToOrder() {
        Intent placeOrder = new Intent(this, OrderActivity.class);
        placeOrder.putExtra("restaurant_name", restaurantName);
        placeOrder.putExtra("student_id", studentID);
        startActivity(placeOrder);
    }

    public void goToMyOrder(View view) {
        Intent myOrder = new Intent(this, MyOrderActivity.class);
        myOrder.putExtra("student_id", studentID);
        startActivity(myOrder);
    }


    // go to login page
    public void logOut(View view) {
        Intent login = new Intent(this, LogInActivity.class);
        startActivity(login);
    }

}
