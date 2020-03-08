package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderDetialActivity extends AppCompatActivity {
    ListView order_info_list;
    TextView order_time, order_price, order_status;
    Button cancel_order_button;
    SQLiteHandler db;
    List<List<String>> menu_info;
    List<String> order_info, menu_name_list, menu_price_list, menus;
    String studentID, order_id, my_orders_time, my_orders_price, my_orders_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detial);
        db = new SQLiteHandler(this);

        Intent intent = getIntent();
        studentID = intent.getStringExtra("student_id");
        order_id = intent.getStringExtra("order_id");

        order_info_list = findViewById(R.id.order_info_list);
        order_time = findViewById(R.id.order_time);
        order_price = findViewById(R.id.order_price);
        order_status = findViewById(R.id.order_status);
        cancel_order_button = findViewById(R.id.cancel_order_button);

        order_info = db.get_orderInfo_by_orderID(order_id);
        my_orders_time = order_info.get(0);
        my_orders_price = order_info.get(1);
        my_orders_status = order_info.get(2);
        order_time.setText(my_orders_time);
        order_price.setText(my_orders_price);
        order_status.setText(my_orders_status);

        menu_info = db.get_menuInfo_by_orderID(order_id);
        menu_name_list = menu_info.get(0);
        menu_price_list = menu_info.get(1);

        menus = new ArrayList<>();
        int n = menu_name_list.size();
        for (int i = 0; i < n; i++) {
            menus.add(menu_name_list.get(i) + "   |   " + menu_price_list.get(i) + "$");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menus);

        order_info_list.setAdapter(adapter);

    }

    public void cancel_order(View view) {
        if (my_orders_status.equals("Placed")) {
            boolean canceled = db.updateOrder(order_id, "Canceled");
            if (canceled) {
                Toast.makeText(this, "Order Canceled!", Toast.LENGTH_SHORT).show();
                // go to my orders
                Intent myOrder = new Intent(this, MyOrderActivity.class);
                myOrder.putExtra("order_id", order_id);
                myOrder.putExtra("student_id", studentID);
                startActivity(myOrder);
            } else {
                Toast.makeText(this, "Order Cannot Be Canceled!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Order Cannot Be Canceled!", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToMyOrder(View view) {
        Intent myOrder = new Intent(this, MyOrderActivity.class);
        myOrder.putExtra("order_id", order_id);
        myOrder.putExtra("student_id", studentID);
        startActivity(myOrder);
    }
}