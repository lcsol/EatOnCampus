package com.example.eatoncampus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    ListView my_order_list;
    Button order_history_button;
    SQLiteHandler db;
    List<List<String>> order_info;
    List<String> my_orders_id, my_orders_time, my_orders_price, my_orders_status, orders;
    String studentID, order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        db = new SQLiteHandler(this);
        Intent stuID = getIntent();
        studentID = stuID.getStringExtra("student_id");

        my_order_list = findViewById(R.id.my_order_list);
//        order_history_button = findViewById(R.id.order_history_button);

        order_info = db.getCurrentOrderInfo(studentID);
        my_orders_id = order_info.get(0);
        my_orders_time = order_info.get(1);
        my_orders_price = order_info.get(2);
        my_orders_status = order_info.get(3);

        orders = new ArrayList<>();
        int n = my_orders_id.size();
        for (int i = 0; i < n; i++) {
            orders.add(my_orders_time.get(i) + "   |   " + my_orders_price.get(i) + "$   |   " + my_orders_status.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orders);

        my_order_list.setAdapter(adapter);

        my_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order_id = my_orders_id.get(position);
                goToOrderDetail();
            }
        });

    }

    private void goToOrderDetail() {
        Intent orderInfo = new Intent(this, OrderDetialActivity.class);
        orderInfo.putExtra("order_id", order_id);
        orderInfo.putExtra("student_id", studentID);
        startActivity(orderInfo);
    }

    public void goToOrderHistory(View view) {

    }

}
