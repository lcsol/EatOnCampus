package com.example.eatoncampus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManagerOrderActivity extends AppCompatActivity {
    ListView maneger_order_list;
    SQLiteHandler db;
    List<List<String>> info;
    List<String> menu_id_list, list, order_info, order_id_list, order_time_list, order_status_list, time_status_list;
    Set<String> order_set;
    Set<Integer> choice;
    String managerID, restaurantName, restaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order);
        db = new SQLiteHandler(this);
        Intent intent = getIntent();
        managerID = intent.getStringExtra("manager_id");
        restaurantName = intent.getStringExtra("restaurant_name");

        info = db.getRestaurantInfo(restaurantName);
//        restaurantID = info.get(0).get(0);
        menu_id_list = info.get(1);
        for (String menu_id : menu_id_list) {
            if (db.checkMenuID(menu_id)) {
                list = db.getRestaurantOrder(menu_id);
                for (String s : list) {
                    order_set.add(s);
                }
            }
        }
        order_id_list = new ArrayList<>();
        for (String s : order_set) {
            order_id_list.add(s);
            order_info = db.getRestaurantOrderInfo(s);
            order_time_list.add(order_info.get(0));
            order_status_list.add(order_info.get(1));
        }

        maneger_order_list = findViewById(R.id.maneger_order_list);



        time_status_list = new ArrayList<>();
        int n = order_time_list.size();
        for (int i = 0; i < n; i++) {
            time_status_list.add(order_time_list.get(i) + "   |   " + order_status_list.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, time_status_list);

        maneger_order_list.setAdapter(adapter);

        maneger_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!choice.add(position)) {
                    choice.remove(position);
                    view.setBackgroundColor(Color.WHITE);
                }
                else view.setBackgroundColor(Color.BLUE);
            }
        });

    }

}
